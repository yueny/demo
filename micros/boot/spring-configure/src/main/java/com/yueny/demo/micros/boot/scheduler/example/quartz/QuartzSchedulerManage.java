package com.yueny.demo.micros.boot.scheduler.example.quartz;

import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.yueny.demo.dynamic.scheduler.job.core.DynamicJob;

import lombok.extern.slf4j.Slf4j;

/**
 * 任务管理器
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年4月26日 下午3:24:31
 *
 */
// @Component
@Slf4j
public class QuartzSchedulerManage implements ApplicationListener<ContextRefreshedEvent> {
	private static final String JOB_GROUP = "event_job_group";
	private static final String TRIGGER_GROUP = "event_trigger_group";
	@Autowired
	private Scheduler scheduler;
	// @Autowired
	// private DynamicSchedulerManager dynamicSchedulerManager;
	@Autowired
	private SchedulerFactoryBean schedulerFactoryBean;

	@Override
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		log.info("开始进行任务调度...");

		try {
			// final Scheduler scheduler = schedulerFactory.getScheduler();

			// dynamicSchedulerManager.startJob(startJob1());
			startJob2(scheduler);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private DynamicJob startJob1() {
		return DynamicJob.builder().target(ScheduledExampleQuartzJob.class).cronExpression("0/5 * * * * ?")
				.jobGroup("group5").jobName("job5").build();
	}

	private void startJob2(final Scheduler scheduler) {
		final String name = "trigger55";
		try {
			final JobDetail jobDetail = JobBuilder.newJob(ScheduledExampleQuartzJob2.class)
					.withIdentity(name, JOB_GROUP).build();
			if (scheduler.checkExists(jobDetail.getKey())) {
				scheduler.deleteJob(jobDetail.getKey());
			}

			final CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/2 * * * * ?");
			final CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(name, TRIGGER_GROUP)
					.withSchedule(scheduleBuilder).build();
			final Date dateTimer = scheduler.scheduleJob(jobDetail, trigger);
			System.out.println(dateTimer);
		} catch (final SchedulerException e) {
			e.printStackTrace();
		}
	}

}
