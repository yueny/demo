package com.yueny.demo.storage.mq.core.factory.product;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;

/**
 * MQProducer管理服务
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年7月13日 上午13:19:30
 */
public class ProductGroupsFactoryManager {
	private static final Logger logger = LoggerFactory.getLogger(ProductGroupsFactoryManager.class);
	private static final Map<String, DefaultMQProducer> mqCachedProducer = new ConcurrentHashMap<String, DefaultMQProducer>();

	public static synchronized void destroy() {
		if (mqCachedProducer == null || mqCachedProducer.isEmpty()) {
			return;
		}

		for (final DefaultMQProducer producer : mqCachedProducer.values()) {
			if (producer != null) {
				// 清理资源，关闭网络连接，注销自己
				producer.shutdown();
			}
		}

		logger.info("注销成功");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	public static synchronized void destroy(final String producerGroup) {
		if (mqCachedProducer == null || mqCachedProducer.isEmpty()) {
			return;
		}

		if (!mqCachedProducer.containsKey(producerGroup)) {
			return;
		}

		final DefaultMQProducer producer = mqCachedProducer.get(producerGroup);
		if (producer != null) {
			// 清理资源，关闭网络连接，注销自己
			/**
			 * 应用退出时，要调用shutdown来清理资源，关闭网络连接，从MetaQ服务器上注销自己
			 * 注意：我们建议应用在JBOSS、Tomcat等容器的退出钩子里调用shutdown方法
			 */
			producer.shutdown();
		}

		logger.info("注销{}成功!", producerGroup);
	}

	/**
	 * 是否存在
	 */
	public static boolean isExist(final String producerGroup) {
		if (mqCachedProducer == null || mqCachedProducer.isEmpty()) {
			return false;
		}

		return mqCachedProducer.containsKey(producerGroup);
	}

	public static synchronized boolean reg(final DefaultMQProducer producer) throws Exception {
		if (mqCachedProducer.containsKey(producer.getProducerGroup())) {
			logger.warn("producer：{} 已注册，跳过~!", producer.getProducerGroup());
			return false;
		}

		mqCachedProducer.put(producer.getProducerGroup(), producer);
		return true;
	}

}
