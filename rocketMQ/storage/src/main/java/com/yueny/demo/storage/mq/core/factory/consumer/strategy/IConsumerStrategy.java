package com.yueny.demo.storage.mq.core.factory.consumer.strategy;

import com.yueny.demo.storage.mq.core.factory.consumer.core.IConsumer;
import com.yueny.superclub.util.strategy.IStrategy;

public interface IConsumerStrategy<T> extends IStrategy<T>, IConsumer {
	// /**
	// * 订阅
	// */
	// boolean subscribe(DefaultMQPushConsumer consumer);

}
