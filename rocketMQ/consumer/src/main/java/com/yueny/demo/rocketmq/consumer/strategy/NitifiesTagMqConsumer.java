package com.yueny.demo.rocketmq.consumer.strategy;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.yueny.demo.rocketmq.MqConstants;
import com.yueny.demo.rocketmq.MqConstants.Tags;
import com.yueny.demo.rocketmq.common.CounterHepler;
import com.yueny.demo.rocketmq.consumer.data.ScallorEvent;
import com.yueny.demo.rocketmq.core.factory.consumer.strategy.AbstractMqConsumer;
import com.yueny.demo.rocketmq.core.factory.consumer.strategy.IConsumer;

/**
 * 普通的 消息订阅
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年3月13日 下午3:15:14
 */
@Service
public class NitifiesTagMqConsumer extends AbstractMqConsumer<ScallorEvent>
		implements IConsumer<MqConstants.Tags>, InitializingBean {

	/**
	 * 如果队列是满的话，报错
	 *
	 * @param events
	 */
	public boolean add(final List<ScallorEvent> events) {
		try {
			return getQueue().addAll(events);
		} catch (final IllegalStateException e) {
			e.printStackTrace();
		} catch (final Throwable e) { // 防御性容错
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 如果队列是满的话，报错
	 *
	 * @param event
	 */
	public boolean add(final ScallorEvent event) {
		return add(Arrays.asList(event));
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// .
	}

	@Override
	public ConsumeConcurrentlyStatus consumer(final MessageExt messageExt) {
		if (consumeMsg(messageExt)) {
			final long rl = CounterHepler.increment();
			System.out.println("Receive: " + rl + " New Messages: " + messageExt.getMsgId());

			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		}
		return ConsumeConcurrentlyStatus.RECONSUME_LATER;
	}

	@Override
	public Tags getCondition() {
		return MqConstants.Tags.NOTIFIES_TAG_MSG;
	}

	private boolean consumeMsg(final MessageExt msg) {
		final ScallorEvent event = new ScallorEvent();
		event.setData(msg.getBody());
		event.setMsgId(msg.getMsgId());

		System.out.println(new String(msg.getBody()) + "。" + Thread.currentThread().getName()
				+ " Receive New Messages: " + msg.toString());

		try {
			// 放入队列 put/add
			return put(event);
		} catch (final IllegalStateException e) {
			e.printStackTrace();
		}
		return false;
	}

}
