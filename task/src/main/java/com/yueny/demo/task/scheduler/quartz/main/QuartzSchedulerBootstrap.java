package com.yueny.demo.task.scheduler.quartz.main;

import java.util.concurrent.TimeUnit;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月19日 下午1:55:13
 *
 */
@Slf4j
public class QuartzSchedulerBootstrap {
	public static void main(final String[] args) {

		try {
			// 获取Scheduler实例
			final Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			// and start it off
			scheduler.start();

			// 具体任务 Define the job to execute
			final JobDetail job = JobBuilder.newJob(DemoSimpleJob.class).withIdentity("demoSimpleJob", "jobFgroup1")
					.build();

			// 触发时间点
			final SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
					.withIntervalInSeconds(5).repeatForever();
			final Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startNow()
					.withSchedule(simpleScheduleBuilder).build();

			// 交由Scheduler安排触发
			scheduler.scheduleJob(job, trigger);

			/* 为观察程序运行，此设置主程序睡眠3分钟才继续往下运行（因下一个步骤是“关闭Scheduler”） */
			try {
				TimeUnit.MINUTES.sleep(3);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}

			// 关闭Scheduler
			scheduler.shutdown();

		} catch (final SchedulerException se) {
			log.error(se.getMessage(), se);
		}

	}
}
