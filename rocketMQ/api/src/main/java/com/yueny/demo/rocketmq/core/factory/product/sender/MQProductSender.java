package com.yueny.demo.rocketmq.core.factory.product.sender;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.yueny.demo.rocketmq.core.factory.product.MQProductFactory;

@Component
public class MQProductSender {
	private static final Logger logger = LoggerFactory.getLogger(MQProductSender.class);

	@Autowired
	private MQProductFactory productFactory;

	/**
	 * @param message
	 * @return 发送结果
	 */
	public SendResult send(final Message message) {
		final DefaultMQProducer producer = productFactory.getProducer();
		if (producer == null) {
			logger.warn("producer is null");
			return null;
		}

		try {
			final SendResult rs = producer.send(message);
			return rs;
		} catch (final Throwable e) {
			logger.error("发送消息失败:", e);
		}
		return null;
	}

	/**
	 * @param topic
	 * @param tags
	 * @param msg
	 *            MQ内容
	 * @return 发送结果
	 */
	public SendResult send(final String topic, final String tags, final String msg) {
		return send(topic, tags, null, msg);
	}

	/**
	 * @param topic
	 * @param tags
	 * @param keys
	 * @param msg
	 *            MQ内容
	 * @return 发送结果
	 */
	public SendResult send(final String topic, final String tags, String keys, final String msg) {
		if (StringUtils.isBlank(topic) || StringUtils.isBlank(tags) || StringUtils.isBlank(msg)) {
			logger.warn("topic | tags | msg is null");
			return null;
		}

		if (StringUtils.isEmpty(keys)) {
			keys = "";
		}
		final Message message = new Message(topic, tags, keys, msg.getBytes());
		return send(message);
	}

}
