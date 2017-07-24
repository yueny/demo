/**
 *
 */
package com.yueny.demo.rocketmq.data;

import com.yueny.demo.rocketmq.enums.CharsetType;

/**
 * data object in notifies
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月19日 上午9:17:21
 *
 */
public interface Event {

	/**
	 * @return 编码集
	 */
	CharsetType getCharset();

	/**
	 * Returns the raw byte array of the data contained in this event.
	 */
	String getData();

	/**
	 * @return 业务方传递的 messageId，用于区别业务标识
	 */
	String getMessageId();

	/**
	 * @param charset
	 *            编码集
	 */
	void setCharset(final CharsetType charset);
	// /**
	// * @param headerType
	// * 头枚举
	// * @param headerContent
	// * header内容
	// */
	// void addHeaders(HeaderType headerType, String headerContent);
	//
	// /**
	// * Map of headers to replace the current headers.
	// */
	// void addHeaders(Map<HeaderType, String> headers);
	// Map<HeaderType, String> getHeaders();

}
