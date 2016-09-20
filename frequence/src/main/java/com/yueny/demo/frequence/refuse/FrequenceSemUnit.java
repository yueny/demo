package com.yueny.demo.frequence.refuse;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import lombok.Getter;
import lombok.ToString;

/**
 * FrequenceSemUnit 频率控制单元(拒绝)
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月15日 下午8:38:47
 *
 */
@ToString
public class FrequenceSemUnit {
	/**
	 * 在指定限频时间{limitSplitTime}内执行响应数量{limitCount}
	 */
	@Getter
	private final int limitCount;
	/**
	 * 限制频率时间,默认1000毫秒(1秒)
	 */
	@Getter
	private Long limitSplitTime = 1000L;
	/**
	 * 正在执行的数目
	 */
	@Getter
	private int realCount = 0;
	private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
		@Override
		public Thread newThread(final Runnable runnable) {
			final Thread thread = new Thread(runnable, "System Clock");
			thread.setDaemon(true);
			return thread;
		}
	});
	/**
	 * 有限许可
	 */
	@Getter
	private final Semaphore sem;

	public FrequenceSemUnit(final Long limitSplitTime, final int limitCount) {
		this.limitSplitTime = limitSplitTime;
		this.limitCount = limitCount;

		this.sem = new Semaphore(limitCount);

		scheduleClockUpdating(sem);
	}

	/**
	 * realCount递归
	 */
	public synchronized void plusRealCount() {
		this.realCount++;
	}

	/**
	 * realCount归零
	 */
	public synchronized void zeroRealCount() {
		this.realCount = 0;
	}

	private void scheduleClockUpdating(final Semaphore sem) {
		// 等待一分钟后resume
		scheduler.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				System.out.println("操作被释放,限流信息:" + sem);

				// 释放其他所有的被占用的许可
				sem.release(getRealCount());
				zeroRealCount();
			}
			// limitSplitTime
		}, 1000, 1000, TimeUnit.MILLISECONDS);
	}
}
