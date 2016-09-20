package com.yueny.demo.lamdba.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yueny.demo.lamdba.test.AbstractBaseSpringTest;

import lombok.SneakyThrows;

/**
 * 单方法的无入参无返回值的landba表达式
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年6月21日 下午9:52:10
 *
 */
public class RunnableTest extends AbstractBaseSpringTest {
	protected final Logger log = LoggerFactory.getLogger(getClass());

	@Test
	@SneakyThrows(value = InterruptedException.class)
	public void testRun() {
		final ExecutorService exec = Executors.newCachedThreadPool();

		for (int i = 0; i < 10; i++) {
			if (i != 0 && i % 3 == 0) {
				Thread.sleep(10L);
			}

			final Runnable run = () -> {
				log.info("{}:testRun", Thread.currentThread().getName());
			};
			exec.execute(run);
		}

	}
}
