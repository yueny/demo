package com.yueny.demo.micros.cloud.spring.context.config.scheduler;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * 异步并行任务基础配置
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年4月26日 下午2:59:20
 *
 */
@Configuration
@EnableScheduling
@EnableAsync(mode = AdviceMode.PROXY, proxyTargetClass = false, order = Ordered.HIGHEST_PRECEDENCE)
public class ScheduleAsyncParallelConfigurer implements AsyncConfigurer, SchedulingConfigurer {
	@Override
	public void configureTasks(final ScheduledTaskRegistrar taskRegistrar) {
		final TaskScheduler scheduler = this.taskScheduler();
		taskRegistrar.setTaskScheduler(scheduler);
	}

	@Override
	public Executor getAsyncExecutor() {
		final Executor executor = this.taskScheduler();
		return executor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new SimpleAsyncUncaughtExceptionHandler();
	}

	@Bean(destroyMethod = "shutdown")
	public ThreadPoolTaskScheduler taskScheduler() {
		final ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize(20);
		scheduler.setThreadNamePrefix("asyncTask-");
		scheduler.setAwaitTerminationSeconds(60);
		scheduler.setWaitForTasksToCompleteOnShutdown(true);

		// 钩子线程里只处理善后，目标是尽可能快的退出且不保证有脏数据。如果钩子线程里做过多事情，或者发生阻塞，那么可能出现kill失效，程序不能退出的情况，这是需要强制退出
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				// We'd like to log progress and failures that may arise in
				// the
				// following code, but unfortunately the behavior of logging
				// is undefined in shutdown hooks.
				// This is because the logging code installs a shutdown hook
				// of its
				// own. See Cleaner class inside {@link LogManager}.
				scheduler.shutdown();
				scheduler.setAwaitTerminationSeconds(120);
				System.out.println("scheduler task completed.");
			}
		});

		return scheduler;
	}

}
