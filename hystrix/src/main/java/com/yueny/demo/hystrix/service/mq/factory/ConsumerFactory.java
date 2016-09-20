package com.yueny.demo.hystrix.service.mq.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections4.MapUtils;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;

import lombok.Setter;

/**
 * 消费工厂
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年3月13日 下午3:16:59
 *
 */
public class ConsumerFactory {
	private static final Map<String, DefaultMQPushConsumer> mqCachedPushTable = new ConcurrentHashMap<>();
	@Setter
	private String namesrvAddr;
	/**
	 * 暂不考虑多个
	 */
	@Setter
	protected String consumerGroup;

	/**
	 * Spring bean destroy-method
	 */
	public void destroy() {
		if (MapUtils.isEmpty(mqCachedPushTable)) {
			return;
		}

		for (final DefaultMQPushConsumer pushConsumer : mqCachedPushTable.values()) {
			pushConsumer.shutdown();
		}
	}

	/**
	 * @return PushConsumer
	 */
	public DefaultMQPushConsumer getPushConsumer() {
		if (!mqCachedPushTable.containsKey(consumerGroup)) {
			/**
			 * 一个应用创建一个Consumer，由应用来维护此对象，可以设置为全局对象或者单例<br>
			 * 注意：ConsumerGroupName需要由应用来保证唯一
			 */
			final DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
			consumer.setNamesrvAddr(namesrvAddr);
			consumer.setInstanceName(String.valueOf(System.currentTimeMillis()));

			// 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
			// 如果非第一次启动，那么按照上次消费的位置继续消费
			consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
			// 设置为集群消费(区别于广播消费)
			consumer.setMessageModel(MessageModel.CLUSTERING);

			mqCachedPushTable.put(consumerGroup, consumer);
		}

		return mqCachedPushTable.get(consumerGroup);
	}

	/**
	 * Spring bean init-method
	 */
	public void init() throws MQClientException {
		// .
	}

}
