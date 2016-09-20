package com.yueny.demo.frequence.block.callable;

import java.util.concurrent.CountDownLatch;

import com.yueny.demo.frequence.block.FrequenceBlockUtils;

/**
 * 模拟xx的测试线程
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月14日 下午7:52:42
 *
 */
public class FrequenceBlockTestRunnable implements Runnable {
	/**
	 * 信号量
	 */
	private final CountDownLatch endLatch;
	private final int limitCount;
	private final long limitTime;
	/**
	 * 信号量
	 */
	private final CountDownLatch startLatch;

	public FrequenceBlockTestRunnable(final long limitTime, final int limitCount) {
		this(limitTime, limitCount, null, null);
	}

	public FrequenceBlockTestRunnable(final long limitTime, final int limitCount, final CountDownLatch startLatch,
			final CountDownLatch endLatch) {
		this.limitTime = limitTime;
		this.limitCount = limitCount;
		this.startLatch = startLatch;
		this.endLatch = endLatch;
	}

	/**
	 * 执行方法
	 *
	 * @param i
	 * @throws InterruptedException
	 */
	public void method(final int i) throws InterruptedException {
		final boolean isLimit = FrequenceBlockUtils.limit(limitTime, limitCount);

		if (!isLimit) {
			System.out.println("block tid:" + Thread.currentThread().getId() + ", i=" + i);
		}
	}

	@Override
	public void run() {
		try {
			if (startLatch != null && endLatch != null) {
				// 等待计数器达到0
				startLatch.await();
			}

			try {
				for (int i = 1; i <= 100; i++) {
					method(i);
				}
			} finally {
				if (startLatch != null && endLatch != null) {
					// 等待计数器达到0
					endLatch.countDown();
				}
			}
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

}
