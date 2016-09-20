package com.yueny.demo.frequence.block;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import com.yueny.demo.frequence.block.callable.FrequenceBlockTestRunnable;

/**
 * 限制调用频率测试用例
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月14日 下午7:51:06
 *
 */
public class FrequenceBlockUtilsTest {
	/**
	 * 单线程
	 */
	@Test
	public void testLimit() throws InterruptedException {
		final Long startTime = System.currentTimeMillis();

		final FrequenceBlockTestRunnable ftc = new FrequenceBlockTestRunnable(1000, 10);
		for (int i = 1; i <= 100; i++) {
			ftc.method(i);
		}

		System.out.println("操作耗时:" + (System.currentTimeMillis() - startTime));

		assertTrue(true);
	}

	/**
	 * 多线程
	 */
	@Test
	public void testLimitMutiThreads() throws InterruptedException {
		final int count = 2;

		// 开始门
		final CountDownLatch startLatch = new CountDownLatch(1);
		// 结束门
		final CountDownLatch endLatch = new CountDownLatch(count);

		for (int i = 0; i < count; i++) {
			final Thread t = new Thread(new FrequenceBlockTestRunnable(1000, 10, startLatch, endLatch));
			t.start();
		}

		final Long startTime = System.currentTimeMillis();
		// 递减计数,如果计数器的值为非0,会一直阻塞不
		startLatch.countDown();
		endLatch.await();

		System.out.println("操作耗时:" + (System.currentTimeMillis() - startTime));

		Thread.sleep(10000L);
		assertTrue(true);
	}

}
