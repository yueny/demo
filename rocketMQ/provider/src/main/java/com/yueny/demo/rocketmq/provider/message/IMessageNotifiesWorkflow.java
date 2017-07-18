package com.yueny.demo.rocketmq.provider.message;

import java.util.List;

import com.yueny.demo.rocketmq.MqConstants;
import com.yueny.demo.rocketmq.data.Event;

/**
 * 消息发送流程
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月19日 上午11:23:48
 *
 */
public interface IMessageNotifiesWorkflow {
	/**
	 * @param event
	 *            事件
	 */
	boolean message(MqConstants.Topic topic, MqConstants.Tags tag, Event event);

	/**
	 * @param event
	 *            事件
	 */
	boolean message(MqConstants.Topic topic, MqConstants.Tags tag, List<Event> event);
}
