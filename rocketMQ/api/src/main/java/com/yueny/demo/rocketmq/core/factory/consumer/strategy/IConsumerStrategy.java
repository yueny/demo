package com.yueny.demo.rocketmq.core.factory.consumer.strategy;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.yueny.superclub.util.strategy.IStrategy;

public interface IConsumerStrategy<T> extends IStrategy<T> {
	/**
	 * 消费
	 */
	ConsumeConcurrentlyStatus consumer(MessageExt messageExt);

	// /**
	// * 订阅
	// */
	// boolean subscribe(DefaultMQPushConsumer consumer);

}
