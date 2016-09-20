package com.yueny.demo.lamdba.listener;

import org.junit.Test;

import com.yueny.demo.lamdba.listener.Listener;
import com.yueny.demo.lamdba.listener.ListenerProcess;
import com.yueny.demo.lamdba.test.AbstractBaseSpringTest;

import lombok.SneakyThrows;

/**
 * 单方法的单入参无返回值的landba表达式
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年6月21日 下午9:55:40
 *
 */
public class ListenerTest extends AbstractBaseSpringTest {
	@Test
	@SneakyThrows(value = InterruptedException.class)
	public void testAction() {
		// final ExecutorService exec = Executors.newCachedThreadPool();

		for (int i = 0; i < 10; i++) {
			if (i != 0 && i % 3 == 0) {
				Thread.sleep(10L);
			}

			final Listener listener = (type) -> {
				logger.info("监听类型:{}的监听行为被运行啦!", type);
			};

			ListenerProcess.reg(String.valueOf(i), listener);
		}

		ListenerProcess.run("5");
		ListenerProcess.run("8");
	}
}
