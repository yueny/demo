/**
 *
 */
package com.yueny.demo.rocketmq.enums;

/**
 * 编码集
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月23日 上午11:00:57
 *
 */
public enum CharsetType {
	/**
	 * GBK
	 */
	GBK("GBK"),
	/**
	 * UTF-8
	 */
	UTF8("UTF-8");

	private final String charset;

	CharsetType(final String charset) {
		this.charset = charset;
	}

	public String charset() {
		return this.charset;
	}
}
