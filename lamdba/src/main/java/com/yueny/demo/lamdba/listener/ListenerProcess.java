package com.yueny.demo.lamdba.listener;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

/**
 * Listener执行器
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年6月21日 下午9:58:23
 *
 */
public class ListenerProcess {
	private static final Map<String, Listener> container = Maps.newConcurrentMap();
	/**
	 * Logger available to subclasses.
	 */
	private static final Logger logger = LoggerFactory.getLogger(ListenerProcess.class);

	/**
	 * 注册
	 *
	 * @param type
	 *            监听类型
	 * @param listener
	 *            Listener
	 */
	public static void reg(final String type, final Listener listener) {
		logger.info("注册:{}-{}.", type, listener);
		container.put(type, listener);
	}

	/**
	 * 注册
	 *
	 * @param type
	 *            监听类型
	 * @param listener
	 *            Listener
	 */
	public static void run(final String type) {
		logger.info("运行:{} 中...", type);
		final Listener listener = container.get(type);

		if (listener == null) {
			return;
		}
		listener.action(type);
	}

}
