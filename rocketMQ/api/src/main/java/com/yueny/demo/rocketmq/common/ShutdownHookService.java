package com.yueny.demo.rocketmq.common;

import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yueny.demo.rocketmq.core.factory.product.helper.asyn.IAsynExecter;

/**
 * 线程Hook
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年7月20日 上午11:13:41
 *
 */
public class ShutdownHookService {
	private static final Logger logger = LoggerFactory.getLogger(ShutdownHookService.class);

	public static void register(final ExecutorService executorService) {
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
				executorService.shutdown();

				logger.info("【线程关闭钩子】已对 ExecutorService 发送关闭请求.");
			}
		});
	}

	public static void register(final IAsynExecter asynExecter) {
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
				asynExecter.shutdown();

				logger.info("【线程关闭钩子】已对 ExecutorService 发送关闭请求.");
			}
		});
	}

}
