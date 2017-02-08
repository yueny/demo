package com.yueny.demo.dynamic.scheduler.job.simple.main;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.common.util.concurrent.AbstractIdleService;
import com.yueny.demo.dynamic.scheduler.job.core.DynamicJob;
import com.yueny.demo.dynamic.scheduler.job.core.factory.DynamicSchedulerManager;
import com.yueny.demo.dynamic.scheduler.job.core.jobbean.demo.SimpleDemoQuartzJob;

/**
 * exampel 1<br>
 * Cron简单触发器
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年12月5日 下午2:15:25
 */
public class SimpleTriggerCronMain extends AbstractIdleService {
	private static Logger logger = LoggerFactory.getLogger(SimpleTriggerCronMain.class);

	public static void main(final String[] args) throws SchedulerException {
		final SimpleTriggerCronMain bootstrap = new SimpleTriggerCronMain();
		bootstrap.startAsync();

		try {
			final Object lock = new Object();
			synchronized (lock) {
				while (true) {
					lock.wait();
				}
			}
		} catch (final InterruptedException ex) {
			System.err.println("ignoreinterruption");
		}
	}

	private ClassPathXmlApplicationContext context;

	@Override
	protected void shutDown() throws Exception {
		// Stop the service.
		context.stop();

		System.out.println("-------------service stoppedsuccessfully-------------");
	}

	@Override
	protected void startUp() throws Exception {
		// Start the service.
		try {
			context = new ClassPathXmlApplicationContext("classpath*:config/all.xml");

			context.start();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		context.registerShutdownHook();
		System.out.println("----------------provider service startedsuccessfully------------");

		final DynamicSchedulerManager dynamicSchedulerManager = (DynamicSchedulerManager) context
				.getBean("dynamicSchedulerManager");
		System.out.println(dynamicSchedulerManager);

		// final Scheduler scheduler = SimpleSchedulerFactory.getScheduler();
		logger.info("------- Scheduling Job  -------------------");

		// job 1 will run every 20 seconds
		final DynamicJob dj1 = DynamicJob.builder().target(SimpleDemoQuartzJob.class).jobName("job1").jobGroup("group1")
				.cronExpression("0/20 * * * * ?").build();
		dynamicSchedulerManager.registerJob(dj1);
		// final JobDetail job1 =
		// newJob(SimpleDemoQuartzJob.class).withIdentity("job1",
		// "group1").build();
		// final CronTrigger trigger1 = newTrigger().withIdentity("trigger1",
		// "group1")
		// .withSchedule(cronSchedule("0/20 * * * * ?")).build();
		// schedule it to run! 交给调度器调度运行JobDetail和Trigger
		// Date ft = scheduler.scheduleJob(job1, trigger1);
		// logger.info(job1.getKey() + " has been scheduled to run at:" + ft + "
		// and repeat based on expression:"
		// + trigger1.getCronExpression());

		// job 2 will run every other minute (at 15 seconds past the minute)
		final DynamicJob dj2 = DynamicJob.builder().target(SimpleDemoQuartzJob.class).jobName("job2").jobGroup("group1")
				.cronExpression("15 0/2 * * * ?").build();
		dynamicSchedulerManager.registerJob(dj2);

		// job 3 will run every other minute but only between 8am and 5pm
		final DynamicJob dj3 = DynamicJob.builder().target(SimpleDemoQuartzJob.class).jobName("job3").jobGroup("group1")
				.cronExpression("0 0/2 8-17 * * ?").build();
		dynamicSchedulerManager.registerJob(dj3);
		// final JobDetail job3 =
		// newJob(SimpleDemoQuartzJob.class).withIdentity("job3",
		// "group1").build();
		// final CronTrigger trigger3 = newTrigger().withIdentity("trigger3",
		// "group1")
		// .withSchedule(cronSchedule("0 0/2 8-17 * * ?")).build();
		// ft = scheduler.scheduleJob(job3, trigger3);
		// logger.info(job3.getKey() + " has been scheduled to run at: " + ft +
		// " and repeat based on expression: "
		// + trigger3.getCronExpression());

		// job 6 will run every 30 seconds but only on Weekdays (Monday through
		// Friday)
		final DynamicJob dj6 = DynamicJob.builder().target(SimpleDemoQuartzJob.class).jobName("job6").jobGroup("group1")
				.cronExpression("0,30 * * ? * MON-FRI").build();
		dynamicSchedulerManager.registerJob(dj6);
	}

}
