package com.yueny.demo.micros.boot.spring.configure.core.context.config.scheduler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import com.google.common.util.concurrent.MoreExecutors;
import com.yueny.superclub.util.exec.async.factory.NamedThreadFactory;

/**
 * 并行任务基础配置
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年4月26日 下午2:51:18
 *
 */
// @Configuration
/*
 * @EnableScheduling
 * 注解的作用是发现注解@Scheduled的任务并后台执行，可以将它看成一个轻量级的Quartz，而且使用起来比Quartz简单许多。
 */
@EnableScheduling
public class ScheduleParallelConfigurer implements SchedulingConfigurer {
	@Override
	public void configureTasks(final ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(taskExecutor());
	}

	@Bean(destroyMethod = "shutdown")
	public ExecutorService taskExecutor() {
		final int threadSize = Runtime.getRuntime().availableProcessors() * 2;// 100

		final ScheduledThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(threadSize,
				/*
				 * new BasicThreadFactory.Builder().namingPattern(
				 * Joiner.on("-").join(namingPattern, "%s")).build()
				 */
				new NamedThreadFactory("executor", true));

		threadPoolExecutor.allowCoreThreadTimeOut(true);

		return MoreExecutors.listeningDecorator(MoreExecutors.getExitingScheduledExecutorService(threadPoolExecutor));
	}

}
