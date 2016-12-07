package com.yueny.demo.dynamic.scheduler.service;

import java.text.ParseException;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

@Deprecated
public class QuartzCronJob {

	public static void main(final String[] args) throws SchedulerException, ParseException {
		final QuartzCronJob cronJob = new QuartzCronJob();
		cronJob.run();
	}

	public void run() throws SchedulerException, ParseException {
		// 获取schedulefactory
		final SchedulerFactory sf = new StdSchedulerFactory("/quartz.properties");

		final Scheduler sch = sf.getScheduler();

		// JobDetail jobDetail=new JobDetailImpl("CronJob", "TEST",
		// CronJob.class);
		//
		// CronTriggerImpl trigger =new CronTriggerImpl("cronTrigger", "TEST");
		//
		// trigger.setCronExpression("0/5 * * * * ?");
		//
		// sch.scheduleJob(jobDetail, trigger);

		sch.start();

	}

}
