package com.yueny.demo.rocketmq.core.factory.consumer.strategy;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractMqConsumer<T> {
	/**
	 * 阻塞队列大小, 默认2000
	 */
	protected static final int DEFAULT_CAPACITY = 2000;
	/**
	 * 阻塞队列大小, 默认2000
	 */
	@Getter
	@Setter
	private int capacity = DEFAULT_CAPACITY;

	/**
	 * MQ数据源内部维护的阻塞队列<br>
	 */
	@Getter
	private final BlockingQueue<T> queue;
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public AbstractMqConsumer() {
		// 阻塞队列,先进先出, 每次操作的是队列头
		queue = new ArrayBlockingQueue<T>(capacity);
	}

	/**
	 * 将指定元素添加到此队列中,如果队列是满的话，会阻塞当前线程
	 *
	 * @param event
	 */
	public boolean put(final T event) {
		try {
			getQueue().put(event);
			return true;
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

}
