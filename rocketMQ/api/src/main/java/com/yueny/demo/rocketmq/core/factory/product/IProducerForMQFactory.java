package com.yueny.demo.rocketmq.core.factory.product;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;

/**
 * mq 生产工厂
 * 
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年7月20日 上午10:41:13
 *
 */
public interface IProducerForMQFactory {
	DefaultMQProducer getProducer();
}
