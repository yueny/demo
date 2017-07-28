package com.yueny.demo.rocketmq.provider.factory;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.yueny.demo.storage.mq.core.factory.product.BaseProductForMQFactory;

import lombok.Setter;

/**
 *
 * 发送 notify 消息的 MQProductFactory
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年7月12日 上午11:19:30
 *
 */
public class NotifyMQProductFactory extends BaseProductForMQFactory {
	@Setter
	private String namesrvAddr;

	@Setter
	private String producerGroup;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.yueny.demo.storage.mq.core.factory.product.BaseProductForMQFactory#
	 * createProducer()
	 */
	@Override
	public DefaultMQProducer createProducer() throws MQClientException {
		final DefaultMQProducer producer = new DefaultMQProducer(producerGroup);

		// eg: 192.168.100.145:9876;192.168.100.146:9876
		producer.setNamesrvAddr(namesrvAddr);
		producer.setInstanceName("Producer" + String.valueOf(System.currentTimeMillis()));
		/*
		 * Producer往MQ上发消息没有发送成功，我们可以设置发送失败重试的次数
		 *
		 * 失败的 情况发送10次
		 */
		producer.setRetryTimesWhenSendFailed(10);

		logger.info("启动成功");
		return producer;
	}

}
