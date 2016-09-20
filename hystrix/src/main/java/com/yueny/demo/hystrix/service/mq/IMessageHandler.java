package com.yueny.demo.hystrix.service.mq;

/**
 * 消息结果处理
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月19日 下午4:16:04
 *
 */
public interface IMessageHandler {
	/**
	 * @param msg
	 *            消息
	 */
	boolean exectue();

}
