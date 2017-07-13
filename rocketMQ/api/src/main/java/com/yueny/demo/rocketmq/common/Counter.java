package com.yueny.demo.rocketmq.common;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public final class Counter {
	/**
	 * 统计
	 */
	private static final ConcurrentMap<String, AtomicLong> counters = new ConcurrentHashMap<String, AtomicLong>();
	private static String DEFAULT_INVOCATION_KEY = "DEFAULT_GROUP";

	/**
	 * 获取默认统计计数器
	 */
	public static AtomicLong getCounter() {
		return getCounter(DEFAULT_INVOCATION_KEY);
	}

	/**
	 * 获取统计计数器
	 */
	public static AtomicLong getCounter(final String invocationKey) {
		AtomicLong counter = counters.get(invocationKey);

		if (counter == null) {
			counters.putIfAbsent(invocationKey, new AtomicLong(0L));
			counter = counters.get(invocationKey);
		}

		return counter;
	}

	/**
	 * 增加默认统计计数器
	 */
	public static boolean set() {
		return set(DEFAULT_INVOCATION_KEY);
	}

	/**
	 * 增加计数器
	 */
	public static boolean set(final String invocationKey) {
		// Initialize the concurrent
		if (!counters.containsKey(invocationKey)) {
			counters.putIfAbsent(invocationKey, new AtomicLong(0L));
		}
		return true;
	}

}
