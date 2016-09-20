/**
 *
 */
package com.yueny.demo.rocketmq.enums;

/**
 * 请求事件类型
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月29日 上午11:24:23
 *
 */
public enum EventRequestType {
	/**
	 * 支付失败
	 */
	PAYMENT_PAY_FAILURE;

	/**
	 *
	 */
	public static EventRequestType getType(final String eventType) {
		for (final EventRequestType type : values()) {
			if (type.name().equals(eventType)) {
				return type;
			}
		}
		return null;
	}
}
