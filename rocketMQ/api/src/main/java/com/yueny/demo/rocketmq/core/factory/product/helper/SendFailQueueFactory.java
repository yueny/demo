package com.yueny.demo.rocketmq.core.factory.product.helper;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 用于存储连续三次发送失败的数据<br>
 * 系统内所有有关获取失败队列的操作均从此处获取
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年3月13日 下午1:12:17
 *
 */
public class SendFailQueueFactory {
	private static Map<String, LinkedBlockingQueue<ProducerSendModel>> queueMap = new ConcurrentHashMap<String, LinkedBlockingQueue<ProducerSendModel>>();
	private static final String RULE_ID = "DEFAULT";

	/**
	 * @param ruleId
	 * @return
	 */
	public static LinkedBlockingQueue<ProducerSendModel> getQueueByDefault() {
		LinkedBlockingQueue<ProducerSendModel> result = queueMap.get(RULE_ID);
		if (result == null) {
			synchronized (queueMap) {
				result = queueMap.get(RULE_ID);
				if (result == null) {
					result = new LinkedBlockingQueue<ProducerSendModel>();
					queueMap.put(RULE_ID, result);
				}
			}
		}
		return result;
	}

	public static void removeQueueByDefault() {
		queueMap.remove(RULE_ID);
	}

}
