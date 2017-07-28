package com.yueny.demo.rocketmq.consumer.factory.trans;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListener;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.yueny.demo.storage.mq.MqConstantsTest;
import com.yueny.demo.storage.mq.core.factory.consumer.BaseConsumerForMQFactory;

import lombok.Getter;
import lombok.Setter;

/**
 * 发送 交易消息的消费工厂<br>
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年3月13日 下午3:16:59
 *
 */
public class TransMQConsumerFactory extends BaseConsumerForMQFactory {
	/**
	 *
	 */
	@Setter
	@Getter
	private String consumerGroup;

	@Autowired
	private TransTagMqConsumer transTagMqConsumer;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.yueny.demo.rocketmq.core.factory.consumer.BaseConsumerForMQFactory#
	 * subscribe(com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer)
	 */
	@Override
	public void subscribe(final DefaultMQPushConsumer consumer) throws MQClientException {
		consumer.subscribe(MqConstantsTest.Topic.MQ_DEMO_TOPIC_TEST.name(),
				MqConstantsTest.TagsT.MQ_TRANS_TAG_MSG.name());
	}

	/**
	 * 消息消费处理
	 */
	private ConsumeConcurrentlyStatus consumeMsgFor(final List<MessageExt> msgs) {
		final MessageExt msg = msgs.get(0);

		try {
			return transTagMqConsumer.consumer(msg);
		} catch (final Exception e) {
			// 每次重发都会导致 msgId 变化，故 msgId 不能作为消息原子幂等的凭据
			logger.error("接收信息 " + msg.getMsgId() + " 处理异常:", e);

			// 当前消息被某个订阅组重新消费了几次（订阅组之间独立计数）
			if (msg.getReconsumeTimes() == getRetryTimes()) {
				// 超过了多少次之后可以不再重试，并记录日志。
				logger.warn("接收信息 {} 处理异常出现" + getRetryTimes() + "次以上，认为成，忽略再次重试。", msg.getMsgId());
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}

			return ConsumeConcurrentlyStatus.RECONSUME_LATER;// 重试
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.yueny.demo.rocketmq.core.factory.consumer.BaseConsumerForMQFactory#
	 * messageListener()
	 */
	@Override
	protected MessageListener messageListener() {
		final MessageListenerConcurrently messageListener = new MessageListenerConcurrently() {
			/**
			 * 默认msgs里只有一条消息，可以通过设置consumeMessageBatchMaxSize参数来批量接收消息
			 */
			@Override
			public ConsumeConcurrentlyStatus consumeMessage(final List<MessageExt> msgs,
					final ConsumeConcurrentlyContext context) {
				return consumeMsgFor(msgs);
			}
		};
		return messageListener;
	}

}
