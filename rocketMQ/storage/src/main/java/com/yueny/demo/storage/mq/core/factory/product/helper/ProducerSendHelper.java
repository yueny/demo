package com.yueny.demo.storage.mq.core.factory.product.helper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;
import com.yueny.demo.storage.mq.common.ShutdownHookService;
import com.yueny.demo.storage.mq.core.factory.product.IProducerForMQFactory;
import com.yueny.demo.storage.mq.data.Event;

/**
 * 生产者消息发送辅助类
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年7月20日 上午10:43:35
 *
 */
public final class ProducerSendHelper {
	private static final ExecutorService executor = Executors.newCachedThreadPool();
	/**
	 * 发送失败数量
	 */
	private static final AtomicLong failCounter = new AtomicLong(0L);
	private static final Logger logger = LoggerFactory.getLogger(ProducerSendHelper.class);
	private static final LinkedBlockingQueue<ProducerSendModel> sendFailQueue;
	/**
	 * 发送成功数量
	 */
	private static final AtomicLong successCounter = new AtomicLong(0L);

	static {
		sendFailQueue = SendFailQueueFactory.getQueueByDefault();

		final SendFailRetrysAsynExecter instance = new SendFailRetrysAsynExecter();

		instance.startup();
		executor.execute(instance);

		ShutdownHookService.register(instance);
		ShutdownHookService.register(executor);
	}

	/**
	 * 消息发送，保证发送成功
	 *
	 * @param producerFactory
	 *            消息发送的指定Factory
	 * @param message
	 *            消息内容体
	 */
	public static void send(final IProducerForMQFactory producerFactory, final Message message) {
		send(producerFactory, message, null);
	}

	/**
	 * 消息发送，保证发送成功
	 *
	 * @param producerFactory
	 *            消息发送的指定Factory
	 * @param message
	 *            消息内容体
	 * @param source
	 *            消息内容体原Event
	 */
	public static void send(final IProducerForMQFactory producerFactory, final Message message, final Event source) {
		if (producerFactory == null) {
			logger.warn("productFactory is null");
			return;
		}

		final DefaultMQProducer producer = producerFactory.getProducer();

		try {
			final SendResult rs = producer.send(message);

			if (rs == null || rs.getSendStatus() != SendStatus.SEND_OK) {
				try {
					logger.warn("发送消息失败，加入重试队列，稍后重试~");
					sendFailQueue
							.put(ProducerSendModel.builder().producerFactory(producerFactory).message(message).build());
				} catch (final Exception ignore) {
					failCounter.incrementAndGet();
					if (source != null) {
						logger.info("发送消息失败,且加入重试队列失败。{}/{} [S]消息msgId/messageId:{}/{}.", successCounter.get(),
								failCounter.get(), rs.getMsgId(), source.getMessageId());
					} else {
						logger.info("发送消息失败,且加入重试队列失败。{}/{} [S]消息msgId/messageId:{}.", successCounter.get(),
								failCounter.get(), rs.getMsgId());
					}
				}
			} else {
				successCounter.incrementAndGet();
				
				if (source != null) {
					logger.info("{}/{} [S]消息msgId/messageId:{}/{}.", successCounter.get(), failCounter.get(),
							rs.getMsgId(), source.getMessageId());
				} else {
					logger.info("{}/{} [S]消息msgId/messageId:{}.", successCounter.get(), failCounter.get(),
							rs.getMsgId());
				}
			}
		} catch (final Exception e) {
			logger.warn("发送消息失败，加入重试队列，稍后重试~");

			try {
				sendFailQueue
						.put(ProducerSendModel.builder().producerFactory(producerFactory).message(message).build());
			} catch (final Exception ignore) {
				logger.error("发送消息失败,且加入重试队列失败:", e);
				e.printStackTrace();
				failCounter.incrementAndGet();
			}
		}
	}
}
