package com.yueny.demo.dynamic.scheduler.job.simple.main;

import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

import org.quartz.DateBuilder;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerMetaData;
import org.quartz.SimpleTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yueny.demo.dynamic.scheduler.job.simple.demo.SimpleDemoQuartzJob;
import com.yueny.demo.dynamic.scheduler.job.simple.factory.SimpleSchedulerFactory;

/**
 * exampel 1<br>
 * 重复执行的简单触发器
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年12月5日 下午2:15:25
 *
 */
public class SimpleTriggerRepeatMain {
	private static Logger log = LoggerFactory.getLogger(SimpleTriggerRepeatMain.class);

	public static void main(final String[] args) throws SchedulerException {
		final Scheduler scheduler = SimpleSchedulerFactory.getScheduler();

		log.info("------- Scheduling Job  -------------------");
		// computer a time that is on the next round minute , get a "nice round"
		// time a few seconds in the future...
		final Date startTime = DateBuilder.nextGivenSecondDate(null, 15);

		// job1 will run 11 times (run once and repeat 10 more times),repeat
		// every 8 seconds
		final JobDetail job1 = newJob(SimpleDemoQuartzJob.class).withIdentity("job1", "group1").build();

		// 设置触发器名称和触发器所属的组名初始化一个触发器
		// 设置触发器触发运行的时间间隔(10 seconds here)
		// 设置触发器触发运行的次数，这里设置运行8，完成后推出
		SimpleTrigger trigger = newTrigger().withIdentity("trigger1", "group1").startAt(startTime)
				.withSchedule(simpleSchedule().withIntervalInSeconds(10).withRepeatCount(8)).build();
		// 设置触发器的优先级，模式为5
		// simpleTrigger.setPriority(10);
		// schedule it to run! 交给调度器调度运行JobDetail和Trigger
		Date ft = scheduler.scheduleJob(job1, trigger);
		log.info(job1.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
				+ trigger.getRepeatInterval() / 1000 + " seconds");

		// the same job (job1) will be scheduled by a another trigger, this time
		// will only repeat twice
		trigger = newTrigger().withIdentity("trigger1", "group2").startAt(startTime)
				.withSchedule(simpleSchedule().withIntervalInSeconds(10).withRepeatCount(2)).forJob(job1).build();
		ft = scheduler.scheduleJob(trigger);
		log.info(job1.getKey() + " will [also] run at: " + ft + " and repeat: " + trigger.getRepeatCount()
				+ " times, every " + trigger.getRepeatInterval() / 1000 + " seconds");

		// job2 will run indefinitely, every 40 seconds
		final JobDetail job2 = newJob(SimpleDemoQuartzJob.class).withIdentity("job2", "group1").build();
		final SimpleTrigger trigger2 = newTrigger().withIdentity("trigger2", "group1").startAt(startTime)
				.withSchedule(simpleSchedule().withIntervalInSeconds(40).repeatForever()).build();
		ft = scheduler.scheduleJob(job2, trigger2);
		log.info(job2.getKey() + " will run at: " + ft + " and repeat: " + trigger2.getRepeatCount() + " times, every "
				+ trigger2.getRepeatInterval() / 1000 + " seconds");

		// job3 will run once, five minutes in the future
		final JobDetail job3 = newJob(SimpleDemoQuartzJob.class).withIdentity("job3", "group1").build();
		final SimpleTrigger trigger3 = (SimpleTrigger) newTrigger().withIdentity("trigger3", "group1")
				.startAt(futureDate(5, IntervalUnit.MINUTE)).build();
		ft = scheduler.scheduleJob(job3, trigger3);
		log.info(job3.getKey() + " will run at: " + ft + " and repeat: " + trigger3.getRepeatCount() + " times, every "
				+ trigger3.getRepeatInterval() / 1000 + " seconds");

		// Start up the scheduler (nothing can actually run until the
		// scheduler has been started) 启动调度器
		scheduler.start();
		log.info("------- Started Scheduler -----------------");

		// wait long enough so that the scheduler as an opportunity to
		// run the job!
		try {
			log.info("------- Waiting 65 seconds... -------------");
			// wait 65 seconds to show job
			Thread.sleep(65L * 1000L);
			// executing...
		} catch (final Exception e) {
			//
		}

		// shut down the scheduler
		log.info("------- Shutting Down ---------------------");
		scheduler.shutdown(true);
		log.info("------- Shutdown Complete -----------------");

		// display some stats about the schedule that just ran
		final SchedulerMetaData metaData = scheduler.getMetaData();
		log.info("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
	}
}
