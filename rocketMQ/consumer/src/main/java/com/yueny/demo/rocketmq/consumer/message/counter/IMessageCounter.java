package com.yueny.demo.rocketmq.consumer.message.counter;

import com.yueny.demo.rocketmq.consumer.message.IComponentType;

public interface IMessageCounter {
	/**
	 * @param delta
	 * @return
	 */
	long addToReceivedCount(IComponentType counterTag, long delta);

	/**
	 * @return 消息接受数目
	 */
	long getReceivedCount(IComponentType counterTag);

	/**
	 * @return 消息接受数目增加
	 */
	long incrementReceivedCount(IComponentType counterTag);
}
