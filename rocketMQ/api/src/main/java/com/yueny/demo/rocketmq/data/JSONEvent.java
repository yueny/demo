/**
 *
 */
package com.yueny.demo.rocketmq.data;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.yueny.demo.rocketmq.enums.CharsetType;
import com.yueny.demo.rocketmq.enums.HeaderType;

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
	@Getter
	private String body;
	/**
	 * 编码集,默认UTF-8
	 */
	@Setter
	@Getter
	private transient CharsetType charset = CharsetType.UTF8;
	private final Map<HeaderType, String> headers = new HashMap<HeaderType, String>();

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.yueny.demo.rocketmq.data.Event#addHeaders(com.yueny.demo.rocketmq.
	 * enums.HeaderType, java.lang.String)
	 */
	public void addHeaders(final HeaderType headerType, final String headerContent) {
		this.headers.put(headerType, headerContent);
	}

	public void addHeaders(final Map<HeaderType, String> headers) {
		this.headers.putAll(headers);
	}

	public Map<HeaderType, String> getHeaders() {
		return headers;
	}

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
