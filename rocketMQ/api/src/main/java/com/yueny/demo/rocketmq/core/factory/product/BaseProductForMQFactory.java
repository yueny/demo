package com.yueny.demo.rocketmq.core.factory.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;

import lombok.Getter;

/**
 * mq 生产工厂<br>
 * Producer<br>
 * 1、一个应用尽可能用一个 Topic，消息子类型用 tags 来标识，tags
 * 可以由应用自由设置。只有发送消息设置了tags，消费方在订阅消息时，才可以利用 tags 在 broker 做消息过滤。
 * 2、每个消息在业务层面的唯一标识码，要设置到 keys 字段，方便将来定位消息丢失问题。由于是哈希索引，请务必保证 key
 * 尽可能唯一，这样可以避免潜在的哈希冲突。 3、消息发送成功或者失败，要打印消息日志，务必要打印 sendresult 和 key 字段。
 * 4、对于消息不可丢失应用，务必要有消息重发机制。例如：消息发送失败，存储到数据库，能有定时程序尝试重发或者人工触发重发。
 * 5、某些应用如果不关注消息是否发送成功，请直接使用sendOneWay方法发送消息。
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年7月12日 上午11:19:30
 */
public abstract class BaseProductForMQFactory implements InitializingBean, DisposableBean {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	@Getter
	private DefaultMQProducer producer;

	public void afterPropertiesSet() throws Exception {
		try {
			producer = createProducer();

			if (ProductGroupsFactoryManager.reg(producer)) {
				producer.start();
				logger.info("producer Started.");
			} else {
				logger.error("producer reg Fail!");
			}
		} catch (final MQClientException e) {
			logger.error("MQ初始化失败");
		}

	}

	/**
	 * 创建Producer， 不需要启动
	 */
	public abstract DefaultMQProducer createProducer() throws MQClientException;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	public void destroy() {
		ProductGroupsFactoryManager.destroy(producer.getProducerGroup());

		logger.info("注销成功");
	}

}
