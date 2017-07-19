package com.yueny.demo.rocketmq.core.factory.consumer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;

/**
 * MQPushConsumer管理服务
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年7月13日 上午13:19:30
 */
public class ConsumerGroupsFactoryManager {
	private static final Logger logger = LoggerFactory.getLogger(ConsumerGroupsFactoryManager.class);
	private static final Map<String, DefaultMQPushConsumer> mqCachedPushTable = new ConcurrentHashMap<String, DefaultMQPushConsumer>();

	/**
	 * Spring bean destroy-method
	 */
	public static synchronized void destroy() {
		if (mqCachedPushTable == null || mqCachedPushTable.isEmpty()) {
			return;
		}

		for (final DefaultMQPushConsumer pushConsumer : mqCachedPushTable.values()) {
			// 清理资源，关闭网络连接，注销自己
			pushConsumer.shutdown();
		}

		logger.info("注销成功");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	public static synchronized void destroy(final String consumerGroup) {
		if (mqCachedPushTable == null || mqCachedPushTable.isEmpty()) {
			return;
		}

		if (!mqCachedPushTable.containsKey(consumerGroup)) {
			return;
		}

		final DefaultMQPushConsumer consumer = mqCachedPushTable.get(consumerGroup);
		if (consumer != null) {
			consumer.shutdown();
		}

		logger.info("注销{}成功!", consumerGroup);
	}

	public static synchronized boolean reg(final DefaultMQPushConsumer consumer) throws Exception {
		if (mqCachedPushTable.containsKey(consumer.getConsumerGroup())) {
			logger.warn("producer：{} 已注册，跳过~!", consumer.getConsumerGroup());
			return false;
		}

		mqCachedPushTable.put(consumer.getConsumerGroup(), consumer);
		return true;
	}

}
