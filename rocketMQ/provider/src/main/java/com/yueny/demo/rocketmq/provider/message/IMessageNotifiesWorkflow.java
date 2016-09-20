package com.yueny.demo.rocketmq.provider.message;

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
	 * @param trys
	 *            次数
	 * @param steps
	 *            步数
	 */
	boolean message(final int trys, int steps);
}
