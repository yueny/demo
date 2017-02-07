package com.yueny.demo.dynamic.scheduler.job.simple.main;

import static org.quartz.DateBuilder.evenMinuteDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yueny.demo.dynamic.scheduler.job.core.jobbean.demo.SimpleDemoQuartzJob;
import com.yueny.demo.dynamic.scheduler.job.simple.factory.SimpleSchedulerFactory;

/**
 * exampel 1<br>
 * 简单触发器
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年12月5日 下午2:15:25
 *
 */
public class SimpleTriggerMain {
	private static Logger log = LoggerFactory.getLogger(SimpleTriggerMain.class);

	public static void main(final String[] args) throws SchedulerException {
		final Scheduler scheduler = SimpleSchedulerFactory.getScheduler();

		log.info("------- Scheduling Job  -------------------");
		// computer a time that is on the next round minute
		// 获取当前时间，初始化触发器的开始日期为下一分钟
		final Date runTime = evenMinuteDate(new Date());

		// define the job and tie it to our HelloJob class
		// 通过设置job name, job group, and executable job class初始化一个JobDetail
		final JobDetail job = newJob(SimpleDemoQuartzJob.class).withIdentity("jobDetail-s1", "jobDetailGroup-s1").build();
		// Trigger the job to run on the next round minute
		// 设置触发器名称和触发器所属的组名初始化一个触发器
		final SimpleTrigger trigger = (SimpleTrigger) newTrigger().withIdentity("simpleTrigger", "triggerGroup1")
				.startAt(runTime).build();
		// 设置触发器触发运行的时间间隔(10 seconds here)
		// trigger.setRepeatInterval(10000);
		// 设置触发器触发运行的次数，这里设置运行100，完成后推出
		// trigger.setRepeatCount(100);
		/**
		 * set the ending time of this job. We set it for 60 seconds from its
		 * startup time here Even if we set its repeat count to 10, this will
		 * stop its process after 6 repeats as it gets it endtime by then.
		 **/
		// // simpleTrigger.setEndTime(new Date(ctime + 60000L));
		// 设置触发器的优先级，模式为5
		// simpleTrigger.setPriority(10);

		// schedule it to run! 交给调度器调度运行JobDetail和Trigger
		final Date ft = scheduler.scheduleJob(job, trigger);
		log.info(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every "
				+ trigger.getRepeatInterval() / 1000 + " seconds");

		// Start up the scheduler (nothing can actually run until the
		// scheduler has been started)
		// 启动调度器
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

	}
}
