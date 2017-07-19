package com.yueny.demo.rocketmq.provider.message.impl;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;
import com.yueny.demo.rocketmq.MqConstants;
import com.yueny.demo.rocketmq.data.Event;
import com.yueny.demo.rocketmq.provider.factory.sender.MQProductSender;
import com.yueny.demo.rocketmq.provider.message.IMessageNotifiesWorkflow;
import com.yueny.rapid.lang.json.JsonUtil;
import com.yueny.rapid.lang.util.StringUtil;

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
	private final Logger logger = LoggerFactory.getLogger(MessageNotifiesWorkflowImpl.class);

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
	public boolean message(final MqConstants.Topic topic, final MqConstants.Tags tag, final Event event) {
		return message(topic, tag, Arrays.asList(event));
	}

	@Override
	public boolean message(final MqConstants.Topic topic, final MqConstants.Tags tag, final List<Event> event) {
		/**
		 * 下面这段代码表明一个Producer对象可以发送多个topic，多个tag的消息。
		 * 注意：send方法是同步调用，只要不抛异常就标识成功。但是发送成功也可会有多种状态，<br>
		 * 例如消息写入Master成功，但是Slave不成功，这种情况消息属于成功，但是对于个别应用如果对消息可靠性要求极高，<br>
		 * 需要对这种情况做处理。另外，消息可能会存在发送失败的情况，失败重试由应用来处理。
		 */
		for (final Event ev : event) {
			try {
				final String json = JsonUtil.toJson(ev);

				// Create a message instance, specifying topic, tag and message
				// body.
				final Message msg = new Message(topic.name(), // topic
						tag.name(), // tag
						(StringUtil.isEmpty(ev.getMessageId()) ? ev.getMessageId() : ""), json.getBytes());

				// This message will be delivered to consumer 10 seconds later.
				// msg.setDelayTimeLevel(3);

				final SendResult sendResult = mqProductSender.send(msg);
				if (sendResult == null || sendResult.getSendStatus() != SendStatus.SEND_OK) {
					failCounter.incrementAndGet();
					logger.info("{}/{} [S]消息msgId/messageId:{}/{} 通知失败!.", successCounter.get(), failCounter.get(),
							sendResult.getMsgId(), ev.getMessageId());
				} else {
					successCounter.incrementAndGet();
					logger.info("{}/{} [S]消息msgId/messageId:{}/{}.", successCounter.get(), failCounter.get(),
							sendResult.getMsgId(), ev.getMessageId());
				}
			} catch (final Exception e) {
				e.printStackTrace();
				failCounter.incrementAndGet();
			}
		}

		return true;
	}

}
