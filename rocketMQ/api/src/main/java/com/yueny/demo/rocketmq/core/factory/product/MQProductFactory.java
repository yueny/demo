package com.yueny.demo.rocketmq.core.factory.product;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;

import lombok.Setter;

/**
 * mq 生产工厂
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年7月12日 上午11:19:30
 *
 */
public class MQProductFactory implements InitializingBean, DisposableBean {
	private static final Logger logger = LoggerFactory.getLogger(MQProductFactory.class);
	private static final Map<String, DefaultMQProducer> mqCachedProducer = new ConcurrentHashMap<String, DefaultMQProducer>();
	@Setter
	private String namesrvAddr;

	private DefaultMQProducer producer;
	@Setter
	private String producerGroup;

	public void afterPropertiesSet() throws Exception {
		init();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	public void destroy() {
		if (mqCachedProducer == null || mqCachedProducer.isEmpty()) {
			return;
		}

		for (final DefaultMQProducer producer : mqCachedProducer.values()) {
			// /**
			// * 应用退出时，要调用shutdown来清理资源，关闭网络连接，从MetaQ服务器上注销自己
			// * 注意：我们建议应用在JBOSS、Tomcat等容器的退出钩子里调用shutdown方法
			// */
			// producer.shutdown();
			if (producer != null) {
				producer.shutdown();
			}
		}

		logger.info("注销成功");
	}

	public DefaultMQProducer getProducer() {
		return producer;
	}

	private void init() throws MQClientException {
		producer = new DefaultMQProducer(producerGroup);
		producer.setNamesrvAddr(namesrvAddr);
		producer.setInstanceName("Producer");
		producer.start();
		logger.info("启动成功");
	}

}
