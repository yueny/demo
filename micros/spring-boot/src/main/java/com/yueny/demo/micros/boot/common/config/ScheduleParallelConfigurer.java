package com.yueny.demo.micros.boot.common.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

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
	public Executor taskExecutor() {
		return Executors.newScheduledThreadPool(100);
	}

}
