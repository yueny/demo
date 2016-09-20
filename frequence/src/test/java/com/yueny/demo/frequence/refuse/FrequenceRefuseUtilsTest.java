package com.yueny.demo.frequence.refuse;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.yueny.demo.frequence.refuse.callable.FrequenceRefuseTestRunnable;

/**
 * 限制调用频率测试用例
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月14日 下午11:28:09
 *
 */
@Deprecated
public class FrequenceRefuseUtilsTest {
	/**
	 * 单线程拒绝
	 */
	@Test
	public void testRefuse() throws InterruptedException {
		final Long startTime = System.currentTimeMillis();

		final FrequenceRefuseTestRunnable ftc = new FrequenceRefuseTestRunnable(1000, 10);
		for (int i = 1; i <= 100000; i++) {
			ftc.method(i);
		}

		System.out.println("操作耗时:" + (System.currentTimeMillis() - startTime));

		Thread.sleep(10000L);
		assertTrue(true);
	}
}
