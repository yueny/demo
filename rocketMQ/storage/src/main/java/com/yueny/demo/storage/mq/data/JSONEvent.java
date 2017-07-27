/**
 *
 */
package com.yueny.demo.storage.mq.data;

import java.util.Date;

import com.yueny.demo.storage.mq.enums.CharsetType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月19日 上午9:18:45
 *
 */
@ToString
@Builder
@AllArgsConstructor
public class JSONEvent implements Event {
	/**
	 * 编码集,默认UTF-8
	 */
	@Setter
	@Getter
	private transient CharsetType charset = CharsetType.UTF8;
	/**
	 * 消息内容
	 */
	@Getter
	@Setter
	private String data;
	/**
	 * 业务方传递的 messageId，用于区别业务标识
	 */
	@Getter
	@Setter
	private String messageId;
	/**
	 * 消息产生时间
	 */
	@Getter
	private final Date timer = new Date();

	// /**
	// * 消息头
	// */
	// private final Map<HeaderType, String> headers = new HashMap<HeaderType,
	// String>();
	// public void addHeaders(final HeaderType headerType, final String
	// headerContent) {
	// this.headers.put(headerType, headerContent);
	// }
	//
	// public void addHeaders(final Map<HeaderType, String> headers) {
	// this.headers.putAll(headers);
	// }
	//
	// public Map<HeaderType, String> getHeaders() {
	// return headers;
	// }

	// public String GetTimer(){
	//
	// }

}
