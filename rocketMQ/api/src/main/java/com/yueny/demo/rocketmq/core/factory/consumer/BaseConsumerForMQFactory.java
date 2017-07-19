package com.yueny.demo.rocketmq.core.factory.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.MessageListener;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerOrderly;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import com.yueny.demo.rocketmq.core.factory.consumer.strategy.DefaultMqConsumerStrategy;

import lombok.Getter;
import lombok.Setter;

/**
 * 消费工厂<br>
 * 消息是以"组group"为维度的<br>
 * Consumer最佳实践<br>
 * 1、消费过程要做到幂等（即消费端去重）<br>
 * 2、尽量使用批量方式消费方式，可以很大程度上提高消费吞吐量。 <br>
 * 3、优化每条消息消费过程<br>
 *
 * Consumer端重试<br>
 * exception的情况，return ConsumeConcurrentlyStatus.RECONSUME_LATER, 则一般重复16次。
 * 10s、30s、1分钟、2分钟、3分钟等等
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年3月13日 下午3:16:59
 *
 */
public abstract class BaseConsumerForMQFactory implements InitializingBean, DisposableBean {
	@Getter
	private DefaultMQPushConsumer consumer;
	@Getter
	@Setter
	private DefaultMqConsumerStrategy defaultMqConsumer = new DefaultMqConsumerStrategy();

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Spring bean init-method
	 *
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		try {
			consumer = createPushConsumer();

			if (ConsumerGroupsFactoryManager.reg(consumer)) {
				consumer.start();
				logger.info("Consumer Started.");
			} else {
				logger.error("Consumer reg Fail!");
			}
		} catch (final MQClientException e) {
			logger.error("订阅消息异常！", e);
		}
	}

	/**
	 * 默认创建Producer的逻辑，如需覆盖，请注意不需要进行启动操作
	 */
	protected DefaultMQPushConsumer createPushConsumer() throws MQClientException {
		/**
		 * 一个应用创建一个Consumer，由应用来维护此对象，可以设置为全局对象或者单例<br>
		 * 注意：ConsumerGroupName需要由应用来保证唯一
		 */
		final DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(getConsumerGroup());
		consumer.setNamesrvAddr(getNamesrvAddr());
		consumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
		/**
		 * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
		 * 如果非第一次启动，那么按照上次消费的位置继续消费
		 */
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		/**
		 * 集群消费只能有一个消费者消费一次，而广播消费则是所有的消费者都会消费一次，但都需要做幂等避免重复消费 <br>
		 * 消费类型： rocketMQ默认是集群消费<br>
		 * 集群消费MessageModel.CLUSTERING, 广播消费MessageModel.BROADCASTING。
		 */
		consumer.setMessageModel(MessageModel.CLUSTERING);
		consumer.setConsumeMessageBatchMaxSize(1);

		// 消费线程池数量 默认10
		// consumer.setConsumeThreadMin();

		subscribe(consumer);

		// MessageListenerOrderly 这个是有序的
		// MessageListenerConcurrently 这个是无序的
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

		final MessageListener listener = messageListener();
		if (listener instanceof MessageListenerOrderly) {
			consumer.registerMessageListener((MessageListenerOrderly) listener);
		} else if (listener instanceof MessageListenerConcurrently) {
			consumer.registerMessageListener((MessageListenerConcurrently) listener);
		}

		return consumer;
	}

	/**
	 * Spring bean destroy-method
	 *
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	public void destroy() throws Exception {
		ConsumerGroupsFactoryManager.destroy(consumer.getConsumerGroup());

		logger.info("注销成功");
	}

	/**
	 * @return consumerGroup
	 */
	public abstract String getConsumerGroup();

	/**
	 * @return namesrvAddr
	 */
	public abstract String getNamesrvAddr();

	/**
	 * MessageListener
	 */
	protected abstract MessageListener messageListener();

	/**
	 * 订阅
	 */
	public abstract void subscribe(DefaultMQPushConsumer consumer) throws MQClientException;

}
