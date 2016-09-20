package com.yueny.demo.rocketmq.data;

import com.yueny.demo.rocketmq.enums.CharsetType;

/**
 * 事件数据对象
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月29日 上午11:42:57
 *
 */
public class EventData {
	/**
	 * 编码集,默认UTF-8
	 */
	private CharsetType charset = CharsetType.UTF8;
	/**
	 * 事件内容
	 */
	private String content;
	/**
	 * 请求事件类型,取自EventRequestType
	 */
	private String eventRequestType;
	/**
	 * 事件主题
	 */
	private String subject;

	/**
	 * @return the charset
	 */
	public CharsetType getCharset() {
		return charset;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @return the eventRequestType
	 */
	public String getEventRequestType() {
		return eventRequestType;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param charset
	 *            the charset to set
	 */
	public void setCharset(final CharsetType charset) {
		this.charset = charset;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(final String content) {
		this.content = content;
	}

	/**
	 * @param eventRequestType
	 *            the eventRequestType to set
	 */
	public void setEventRequestType(final String eventRequestType) {
		this.eventRequestType = eventRequestType;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(final String subject) {
		this.subject = subject;
	}

}
