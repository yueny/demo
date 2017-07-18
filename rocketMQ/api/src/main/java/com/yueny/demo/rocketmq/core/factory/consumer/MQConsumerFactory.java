package com.yueny.demo.rocketmq.core.factory.consumer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import com.yueny.demo.rocketmq.MqConstants;
import com.yueny.demo.rocketmq.core.factory.consumer.strategy.DefaultMqConsumer;
import com.yueny.demo.rocketmq.core.factory.consumer.strategy.IConsumer;
import com.yueny.superclub.util.strategy.container.IStrategyContainer;
import com.yueny.superclub.util.strategy.container.StrategyContainerImpl;

import lombok.Setter;

/**
 * 消费工厂<br>
 * 消息是以"组group"为维度的<br>
 * Consumer最佳实践<br>
 * 1、消费过程要做到幂等（即消费端去重）<br>
 * 2、尽量使用批量方式消费方式，可以很大程度上提高消费吞吐量。 <br>
 * 3、优化每条消息消费过程<br>
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年3月13日 下午3:16:59
 *
 */
public class MQConsumerFactory {
	private static final Logger logger = LoggerFactory.getLogger(MQConsumerFactory.class);
	private static final Map<String, DefaultMQPushConsumer> mqCachedPushTable = new ConcurrentHashMap<String, DefaultMQPushConsumer>();

	/**
	 * 暂不考虑多个
	 */
	@Setter
	protected String consumerGroup;
	private final IStrategyContainer<MqConstants.Tags, IConsumer<MqConstants.Tags>> container = new StrategyContainerImpl<MqConstants.Tags, IConsumer<MqConstants.Tags>>() {
	};
	private final DefaultMqConsumer defaultMqConsumer = new DefaultMqConsumer();
	@Setter
	private String namesrvAddr;

	private ConsumeConcurrentlyStatus consumeMsgFor(final List<MessageExt> msgs) {
		try {
			final MessageExt msg = msgs.get(0);

			final IConsumer<MqConstants.Tags> strategy = container
					.getStrategy(MqConstants.Tags.valueOf(msg.getTags().trim()));
			if (strategy == null) {
				return defaultMqConsumer.consumer(msg);
			}
			return strategy.consumer(msg);
		} catch (final Exception e) {
			logger.error("接收信息处理异常:", e);
			return ConsumeConcurrentlyStatus.RECONSUME_LATER;
		}
	}

	/**
	 * Spring bean destroy-method
	 */
	public void destroy() {
		if (mqCachedPushTable == null || mqCachedPushTable.isEmpty()) {
			return;
		}

		for (final DefaultMQPushConsumer pushConsumer : mqCachedPushTable.values()) {
			// 清理资源，关闭网络连接，注销自己
			pushConsumer.shutdown();
		}
	}

	/**
	 * @return PushConsumer
	 */
	private DefaultMQPushConsumer getPushConsumer() {
		if (!mqCachedPushTable.containsKey(consumerGroup)) {
			try {
				/**
				 * 一个应用创建一个Consumer，由应用来维护此对象，可以设置为全局对象或者单例<br>
				 * 注意：ConsumerGroupName需要由应用来保证唯一
				 */
				final DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
				consumer.setNamesrvAddr(namesrvAddr);
				consumer.setInstanceName(String.valueOf(System.currentTimeMillis()));

				// 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
				// 如果非第一次启动，那么按照上次消费的位置继续消费
				consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
				// 设置为集群消费(区别于广播消费)
				consumer.setMessageModel(MessageModel.CLUSTERING);
				consumer.setConsumeMessageBatchMaxSize(1);

				// 订阅
				// consumer.subscribe("TopicTest", "TagA || TagC || TagD");
				consumer.subscribe(MqConstants.Topic.MQ_DEMO_TOPIC_TEST.name(),
						MqConstants.Tags.MQ_DEMO_TAG_MSG.name());

				// consumer.registerMessageListener(new MessageListenerOrderly()
				// {
				// AtomicLong consumeTimes = new AtomicLong(0);
				//
				// public ConsumeOrderlyStatus consumeMessage(final
				// List<MessageExt>
				// msgs,
				// final ConsumeOrderlyContext context) {
				// context.setAutoCommit(false);
				// System.out.printf(Thread.currentThread().getName() + "
				// Receive
				// New Messages: " + msgs + "%n");
				// this.consumeTimes.incrementAndGet();
				// if ((this.consumeTimes.get() % 2) == 0) {
				// return ConsumeOrderlyStatus.SUCCESS;
				// } else if ((this.consumeTimes.get() % 3) == 0) {
				// return ConsumeOrderlyStatus.ROLLBACK;
				// } else if ((this.consumeTimes.get() % 4) == 0) {
				// return ConsumeOrderlyStatus.COMMIT;
				// } else if ((this.consumeTimes.get() % 5) == 0) {
				// context.setSuspendCurrentQueueTimeMillis(3000);
				// return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
				// }
				// return ConsumeOrderlyStatus.SUCCESS;
				// }
				// });
				consumer.registerMessageListener(new MessageListenerConcurrently() {
					/**
					 * 默认msgs里只有一条消息，可以通过设置consumeMessageBatchMaxSize参数来批量接收消息
					 */
					public ConsumeConcurrentlyStatus consumeMessage(final List<MessageExt> msgs,
							final ConsumeConcurrentlyContext context) {
						return consumeMsgFor(msgs);
					}
				});

				consumer.start();
				logger.info("Consumer Started.");

				mqCachedPushTable.put(consumerGroup, consumer);
			} catch (final MQClientException e) {
				logger.error("订阅消息异常:{}", e);
			}

		}

		return mqCachedPushTable.get(consumerGroup);
	}

	/**
	 * Spring bean init-method
	 */
	public void init() throws MQClientException {
		getPushConsumer();
	}

}
