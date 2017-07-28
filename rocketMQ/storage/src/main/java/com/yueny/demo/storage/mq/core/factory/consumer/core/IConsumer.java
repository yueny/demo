package com.yueny.demo.storage.mq.core.factory.consumer.core;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.common.message.MessageExt;

/**
 * 消费者服务
 * 
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年7月28日 下午5:25:01
 *
 */
public interface IConsumer {
	/**
	 * 消费
	 */
	ConsumeConcurrentlyStatus consumer(MessageExt messageExt);

}
