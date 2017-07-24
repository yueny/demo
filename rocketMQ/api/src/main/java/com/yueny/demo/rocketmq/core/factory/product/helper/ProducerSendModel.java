package com.yueny.demo.rocketmq.core.factory.product.helper;

import com.alibaba.rocketmq.common.message.Message;
import com.yueny.demo.rocketmq.core.factory.product.IProducerForMQFactory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年7月20日 上午11:00:32
 *
 */
@Builder
@AllArgsConstructor
public class ProducerSendModel {
	/**
	 * 发送消息体
	 */
	@Getter
	@Setter
	private Message message;
	/**
	 * mq 生产工厂
	 */
	@Getter
	@Setter
	private IProducerForMQFactory producerFactory;
}
