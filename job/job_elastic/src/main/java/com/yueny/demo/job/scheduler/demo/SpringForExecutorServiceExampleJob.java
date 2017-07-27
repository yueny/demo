package com.yueny.demo.job.scheduler.demo;

import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.base.Joiner;
import com.google.common.util.concurrent.MoreExecutors;
import com.yueny.demo.job.scheduler.base.BaseSuperScheduler;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年4月18日 下午6:26:57
 *
 */
// @Component
public class SpringForExecutorServiceExampleJob extends BaseSuperScheduler implements InitializingBean {
	/**
	 * 定时任务执行器
	 */
	private static ScheduledExecutorService scheduledExecutorService = MoreExecutors.listeningDecorator(MoreExecutors
			.getExitingScheduledExecutorService(new ScheduledThreadPoolExecutor(
					Runtime.getRuntime().availableProcessors() * 2, new BasicThreadFactory.Builder()
							.namingPattern(Joiner.on("-").join("asynTask", "%s")).build())));

	// private static ScheduledExecutorService scheduledExecutorService1 =
	// Executors.newScheduledThreadPool(
	// Runtime.getRuntime().availableProcessors() * 2, new
	// NamedThreadFactory("asynTask", true));

	// 定时器
	private ScheduledFuture<?> channelFuture;

	@Override
	public void afterPropertiesSet() throws Exception {
		channelFuture = scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println("SpringForExecutorServiceExampleJob:" + new Date());
				} catch (final Throwable t) { // 防御性容错
					System.out.println(t.getMessage());
					logger.error("Unexpected error occur at channel monitor, cause: " + t.getMessage(), t);
				}
			}
		},
				// 初始化延时
				1000L,
				// 前一次执行结束到下一次执行开始的间隔时间（间隔执行延迟时间）
				5L * 1000,
				// 计时单位
				TimeUnit.MILLISECONDS);
	}

}
