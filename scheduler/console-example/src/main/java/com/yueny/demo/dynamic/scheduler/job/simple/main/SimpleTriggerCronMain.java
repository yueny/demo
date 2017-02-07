package com.yueny.demo.dynamic.scheduler.job.simple.main;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerMetaData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yueny.demo.dynamic.scheduler.job.simple.SimpleQuartzJob;
import com.yueny.demo.dynamic.scheduler.job.simple.factory.SimpleSchedulerFactory;

/**
 * exampel 1<br>
 * Cron简单触发器
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年12月5日 下午2:15:25
 *
 */
public class SimpleTriggerCronMain {
	private static Logger log = LoggerFactory.getLogger(SimpleTriggerCronMain.class);

	public static void main(final String[] args) throws SchedulerException {
		final Scheduler scheduler = SimpleSchedulerFactory.getScheduler();

		log.info("------- Scheduling Job  -------------------");
		// job 1 will run every 20 seconds
		final JobDetail job1 = newJob(SimpleQuartzJob.class).withIdentity("job1", "group1").build();
		final CronTrigger trigger1 = newTrigger().withIdentity("trigger1", "group1")
				.withSchedule(cronSchedule("0/20 * * * * ?")).build();
		// schedule it to run! 交给调度器调度运行JobDetail和Trigger
		Date ft = scheduler.scheduleJob(job1, trigger1);
		log.info(job1.getKey() + " has been scheduled to run at:" + ft + " and repeat based on expression:"
				+ trigger1.getCronExpression());

		// job 2 will run every other minute (at 15 seconds past the minute)
		final JobDetail job2 = newJob(SimpleQuartzJob.class).withIdentity("job2", "group1").build();
		final CronTrigger trigger2 = newTrigger().withIdentity("trigger2", "group1")
				.withSchedule(cronSchedule("15 0/2 * * * ?")).build();
		ft = scheduler.scheduleJob(job2, trigger2);
		log.info(job2.getKey() + " has been scheduled to run at: " + ft + " and repeat based on expression: "
				+ trigger2.getCronExpression());

		// job 3 will run every other minute but only between 8am and 5pm
		final JobDetail job3 = newJob(SimpleQuartzJob.class).withIdentity("job3", "group1").build();
		final CronTrigger trigger3 = newTrigger().withIdentity("trigger3", "group1")
				.withSchedule(cronSchedule("0 0/2 8-17 * * ?")).build();
		ft = scheduler.scheduleJob(job3, trigger3);
		log.info(job3.getKey() + " has been scheduled to run at: " + ft + " and repeat based on expression: "
				+ trigger3.getCronExpression());

		// job 6 will run every 30 seconds but only on Weekdays (Monday through
		// Friday)
		final JobDetail job6 = newJob(SimpleQuartzJob.class).withIdentity("job6", "group1").build();
		final CronTrigger trigger6 = newTrigger().withIdentity("trigger6", "group1")
				.withSchedule(cronSchedule("0,30 * * ? * MON-FRI")).build();
		ft = scheduler.scheduleJob(job6, trigger6);
		log.info(job6.getKey() + " has been scheduled to run at: " + ft + " and repeat based on expression: "
				+ trigger6.getCronExpression());

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
