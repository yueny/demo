package com.yueny.demo.rocketmq.consumer.factory.strategy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.yueny.demo.rocketmq.MqConstants;
import com.yueny.demo.rocketmq.common.CounterHepler;
import com.yueny.demo.rocketmq.consumer.data.ScallorEvent;
import com.yueny.demo.rocketmq.core.factory.consumer.strategy.AbstractMqConsumerStrategy;
import com.yueny.demo.rocketmq.core.factory.consumer.strategy.IConsumerStrategy;
import com.yueny.demo.rocketmq.data.JSONEvent;
import com.yueny.rapid.lang.json.JsonUtil;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年7月12日 下午2:25:26
 *
 */
@Service
public class DemoTagMqConsumerStrategy extends AbstractMqConsumerStrategy<ScallorEvent>
		implements IConsumerStrategy<MqConstants.Tags> {
	/**
	 * 数据组装任务
	 */
	private class AssemblyEventDataTask implements Runnable {
		@Override
		public void run() {
			while (true) {
				ScallorEvent event = null;
				try {
					event = getQueue().poll(500L, TimeUnit.MILLISECONDS);

					if (event == null) {
						try {
							TimeUnit.MILLISECONDS.sleep(500L);
						} catch (final Exception e) {
							logger.error("监听" + getCondition() + "消息无数据，等待下次操作！");
						}
					}
				} catch (final InterruptedException ignore) {
					continue;
				}

				if (event == null) {
					continue;
				}

				try {
					CounterHepler.increment(GROUP_FOR_OPERA);

					logger.info("{} -->完成{}消息处理 in {} :{}/{}.", CounterHepler.get(GROUP_FOR_OPERA), getCondition(),
							Thread.currentThread().getName(), event.getMsgId(), event.getMessageId());
				} catch (final Exception e) {
					put(event);
					logger.error("监听" + getCondition() + "消息异常，重新入池进行等待下次操作！", e);
				}
			}
		}
	}

	private static String GROUP_FOR_CONSUMER = "CONSUMER";
	private static String GROUP_FOR_OPERA = "OPERA";

	/**
	 * 只支持一个线程的线程池，配置corePoolSize=maximumPoolSize=1，无界阻塞队列LinkedBlockingQueue；
	 * 保证任务由一个线程串行执行
	 */
	private final ExecutorService executorService = Executors.newSingleThreadExecutor();

	public DemoTagMqConsumerStrategy() {
		super();

		executorService.execute(new AssemblyEventDataTask());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.yueny.demo.rocketmq.core.factory.consumer.strategy.IConsumer#consumer
	 * (com.alibaba.rocketmq.common.message.MessageExt)
	 */
	@Override
	public ConsumeConcurrentlyStatus consumer(final MessageExt messageExt) {
		// body and json is JSONEvent
		final String json = new String(messageExt.getBody(), MqConstants.DEFAULT_CHARSET_TYPE);
		logger.debug("接收数据：{}.", json);

		final JSONEvent jsonEvent = JsonUtil.fromJson(json, JSONEvent.class);

		final ScallorEvent event = ScallorEvent.builder().msgId(messageExt.getMsgId()).data(jsonEvent.getData())
				.messageId(jsonEvent.getMessageId()).build();

		// RocketMQ不保证消息不重复，如果你的业务需要保证严格的不重复消息，需要你自己在业务端去重。
		// 消费端处理消息的业务逻辑保持幂等性
		// 保证每条消息都有唯一编号且保证消息处理成功与去重表的日志同时出现

		try {
			// 放入队列 put/add
			if (super.put(event)) {
				final long rl = CounterHepler.increment(GROUP_FOR_CONSUMER);

				// (System.currentTimeMillis() - msg.getStoreTimestamp())
				logger.info("{} --> Receive MessagesID[{}/{}]:{}.", rl, getCondition(), event.getMsgId(),
						event.getMessageId());
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return ConsumeConcurrentlyStatus.RECONSUME_LATER;
	}

	@Override
	public MqConstants.Tags getCondition() {
		return MqConstants.Tags.MQ_DEMO_TAG_MSG;
	}

}
