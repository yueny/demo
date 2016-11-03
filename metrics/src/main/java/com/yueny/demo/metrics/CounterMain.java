package com.yueny.demo.metrics;

import static com.codahale.metrics.MetricRegistry.name;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.DelayQueue;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.codahale.metrics.Counter;
import com.yueny.demo.metrics.scheduler.task.DelayTask;
import com.yueny.demo.metrics.util.MetricRegistryHelperDemo;

/**
 * 计数器<br>
 * Counter是一个AtomicLong实例， 可以增加或者减少值。 例如，可以用它来计数队列中加入的Job的总数。
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年10月27日 上午11:39:11
 *
 */
public class CounterMain {
	public static DelayQueue<DelayTask> tasks = new DelayQueue<DelayTask>();
	private static final Counter pendingJobs = MetricRegistryHelperDemo.getInstance().getReg()
			.counter(name(TimerMain.class, "pending-jobs"));
	private static ThreadPoolTaskExecutor poolTaskExecutor;
	static {
		poolTaskExecutor = new ThreadPoolTaskExecutor();
		poolTaskExecutor.setCorePoolSize(2);// 线程池维护线程的最少数量
		poolTaskExecutor.setKeepAliveSeconds(10000);// 线程池维护线程所允许的空闲时间
		poolTaskExecutor.setMaxPoolSize(5);// 线程池维护线程的最大数量
		poolTaskExecutor.setQueueCapacity(50);// 线程池所使用的缓冲队列
		poolTaskExecutor.initialize();
	}

	public static void addJob(final DelayTask job) {
		pendingJobs.inc();
		tasks.put(job);
	}

	public static void main(final String args[]) {
		final Random rn = new Random(5L);
		for (int i = 0; i < 8; i++) {
			final Long notifyTimes = rn.nextLong() + 10;
			addJob(new DelayTask(new Date(), notifyTimes));
		}

		poolTaskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						Thread.sleep(50);// 50毫秒执行一次

						// 获取当前线程池活动的线程数. 如果当前活动线程等于最大线程，那么不执行
						if (poolTaskExecutor.getActiveCount() < poolTaskExecutor.getMaxPoolSize()) {
							final DelayTask task = tasks.poll();
							if (task != null) {
								poolTaskExecutor.execute(new Runnable() {
									@Override
									public void run() {
										System.out.println(poolTaskExecutor.getActiveCount() + "---------");
										takeJob(task);
										task.run();
									}
								});
							}
						}
					}
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static boolean takeJob(final DelayTask job) {
		pendingJobs.dec();
		return tasks.remove(job);
	}

}
