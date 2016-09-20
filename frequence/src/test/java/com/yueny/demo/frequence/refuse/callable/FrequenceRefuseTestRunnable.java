package com.yueny.demo.frequence.refuse.callable;

import com.yueny.demo.frequence.refuse.FrequenceRefuseUtils;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月15日 下午1:54:19
 *
 */
public class FrequenceRefuseTestRunnable implements Runnable {
	private final int limitCount;
	private final long limitTime;

	public FrequenceRefuseTestRunnable(final long limitTime, final int limitCount) {
		this.limitTime = limitTime;
		this.limitCount = limitCount;
	}

	/**
	 * 执行方法
	 *
	 * @param i
	 * @throws InterruptedException
	 */
	public void method(final int i) throws InterruptedException {
		final boolean isRefuse = FrequenceRefuseUtils.refuse("method", limitTime, limitCount);

		// 没有拒绝
		if (!isRefuse) {
			System.out.println("refuse tid:" + Thread.currentThread().getId() + ", i=" + i);
		}
	}

	@Override
	public void run() {
		try {
			for (int i = 1; i <= 100; i++) {
				method(i);
			}
		} catch (final InterruptedException e) {
			e.printStackTrace();
		} finally {
			//
		}
	}
}
