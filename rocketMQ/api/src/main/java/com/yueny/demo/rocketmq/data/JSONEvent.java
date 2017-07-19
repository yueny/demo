/**
 *
 */
package com.yueny.demo.rocketmq.data;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import com.yueny.demo.rocketmq.enums.CharsetType;

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
public class JSONEvent implements Event {
	/**
	 * 消息内容，该内容中不允许出现Map结构，避免出现JSON格式问题
	 */
	@Getter
	private String body;
	/**
	 * 编码集,默认UTF-8
	 */
	@Setter
	@Getter
	private transient CharsetType charset = CharsetType.UTF8;
	/**
	 * 业务方传递的 messageId，用于区别业务标识
	 */
	@Getter
	@Setter
	private String messageId;
	/**
	 * 消息产生时间
	 */
	@Setter
	private Date timer;
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

	public void setBody(final byte[] body) {
		if (body != null) {
			try {
				this.body = new String(body, charset.charset());
			} catch (final UnsupportedEncodingException e) {
				// exception
				e.printStackTrace();
			}
		} else {
			this.body = "";
		}
	}

}
