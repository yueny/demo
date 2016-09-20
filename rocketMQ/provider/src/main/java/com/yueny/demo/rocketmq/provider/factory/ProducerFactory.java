package com.yueny.demo.rocketmq.provider.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections4.MapUtils;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;

import lombok.Setter;

/**
 * 生产工厂
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年3月13日 下午3:42:35
 *
 */
public class ProducerFactory {
	private static final Map<String, DefaultMQProducer> mqCachedProducer = new ConcurrentHashMap<>();
	/**
	 *
	 */
	@Setter
	private String namesrvAddr;
	/**
	 * 暂不考虑多个
	 */
	@Setter
	protected String producerGroup;

	/**
	 * Spring bean destroy-method
	 */
	public void destroy() {
		if (MapUtils.isEmpty(mqCachedProducer)) {
			return;
		}

		for (final DefaultMQProducer producer : mqCachedProducer.values()) {
			producer.shutdown();
		}
		// /**
		// * 应用退出时，要调用shutdown来清理资源，关闭网络连接，从MetaQ服务器上注销自己
		// * 注意：我们建议应用在JBOSS、Tomcat等容器的退出钩子里调用shutdown方法
		// */
		// producer.shutdown();
	}

	/**
	 * @return 获取消息生产provider
	 * @throws MQClientException
	 */
	public DefaultMQProducer getProducer() throws MQClientException {
		if (!mqCachedProducer.containsKey(producerGroup)) {
			/**
			 * 一个应用创建一个Producer，由应用来维护此对象，可以设置为全局对象或者单例<br>
			 * 注意：ProducerGroupName需要由应用来保证唯一<br>
			 * ProducerGroup这个概念发送普通的消息时，作用不大，但是发送分布式事务消息时，比较关键，
			 * 因为服务器会回查这个Group下的任意一个Producer
			 */
			final DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
			producer.setNamesrvAddr(namesrvAddr);
			producer.setInstanceName(String.valueOf(System.currentTimeMillis()));
			/**
			 * Producer对象在使用之前必须要调用start初始化，初始化一次即可<br>
			 * 注意：切记不可以在每次发送消息时，都调用start方法
			 */
			producer.start();

			mqCachedProducer.put(producerGroup, producer);
		}

		return mqCachedProducer.get(producerGroup);
	}

	/**
	 * Spring bean init-method
	 */
	public void init() throws MQClientException {
		// .
	}

}
