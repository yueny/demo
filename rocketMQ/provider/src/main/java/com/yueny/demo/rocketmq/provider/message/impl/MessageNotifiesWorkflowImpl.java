package com.yueny.demo.rocketmq.provider.message.impl;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;
import com.yueny.demo.rocketmq.MqConstants;
import com.yueny.demo.rocketmq.core.factory.product.sender.MQProductSender;
import com.yueny.demo.rocketmq.data.Event;
import com.yueny.demo.rocketmq.provider.message.IMessageNotifiesWorkflow;
import com.yueny.rapid.lang.json.JsonUtil;

/**
 * 消息流程
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年3月13日 下午3:48:24
 *
 */
@Service
public class MessageNotifiesWorkflowImpl implements IMessageNotifiesWorkflow, InitializingBean {
	/**
	 * 发送失败数量
	 */
	private final AtomicLong failCounter = new AtomicLong(0L);

	@Autowired
	private MQProductSender mqProductSender;
	/**
	 * 发送成功数量
	 */
	private final AtomicLong successCounter = new AtomicLong(0L);

	@Override
	public void afterPropertiesSet() {
		// .
	}

	@Override
	public boolean message(final Event event) {
		return message(Arrays.asList(event));
	}

	@Override
	public boolean message(final List<Event> event) {
		System.out.println("---------------------- message start ----------------------");

		/**
		 * 下面这段代码表明一个Producer对象可以发送多个topic，多个tag的消息。
		 * 注意：send方法是同步调用，只要不抛异常就标识成功。但是发送成功也可会有多种状态，<br>
		 * 例如消息写入Master成功，但是Slave不成功，这种情况消息属于成功，但是对于个别应用如果对消息可靠性要求极高，<br>
		 * 需要对这种情况做处理。另外，消息可能会存在发送失败的情况，失败重试由应用来处理。
		 */
		for (final Event ev : event) {
			try {
				final String json = JsonUtil.toJson(ev);
				final Message msg = new Message(MqConstants.Topic.NOTIFIES_MQ_TOPIC.topic(), // topic
						MqConstants.Tags.NOTIFIES_TAG_WARNING.tag(), // tag
						json.getBytes());
				final SendResult sendResult = mqProductSender.send(msg);

				if (sendResult == null || sendResult.getSendStatus() != SendStatus.SEND_OK) {
					System.out.println("消息:" + json + " 通知失败,result:" + sendResult);
					failCounter.incrementAndGet();
				} else {
					System.out.println("[S]消息:" + json);
					successCounter.incrementAndGet();
				}
			} catch (final Exception e) {
				e.printStackTrace();
				failCounter.incrementAndGet();
			}
		}

		System.out.println("消息批量推送结束, 已成功/失败发送总数量:" + successCounter.get() + "/" + failCounter.get());
		System.out.println("---------------------- message end ----------------------");
		return true;
	}

}
