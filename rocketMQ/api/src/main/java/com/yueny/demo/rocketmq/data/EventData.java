package com.yueny.demo.rocketmq.data;

import java.util.HashMap;
import java.util.Map;

import com.yueny.demo.rocketmq.enums.CharsetType;
import com.yueny.demo.rocketmq.enums.HeaderType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 事件数据对象
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月29日 上午11:42:57
 *
 */
@ToString
public class EventData implements Event {
	/**
	 * 事件内容
	 */
	@Getter
	@Setter
	private String body;
	/**
	 * 编码集,默认UTF-8
	 */
	@Getter
	@Setter
	private CharsetType charset = CharsetType.UTF8;
	/**
	 * 请求事件类型,取自EventRequestType
	 */
	@Getter
	@Setter
	private String eventRequestType;
	private final Map<HeaderType, String> headers = new HashMap<HeaderType, String>();
	/**
	 * 事件主题
	 */
	@Getter
	@Setter
	private String subject;

	public void addHeaders(final HeaderType headerType, final String headerContent) {
		this.headers.put(headerType, headerContent);
	}

	public void addHeaders(final Map<HeaderType, String> headers) {
		this.headers.putAll(headers);
	}

	public Map<HeaderType, String> getHeaders() {
		return headers;
	}

}
