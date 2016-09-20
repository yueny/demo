package com.yueny.demo.frequence.sla;

/**
 * 访问太频繁.
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月15日 下午2:49:29
 *
 */
public class FrequencyException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * @param target
	 *            访问对象
	 * @param uri
	 *            访问源
	 */
	public FrequencyException(final String uri) {
		super("您访问[" + uri + "]太频繁了，歇一会儿吧.");
		// final Object target,
		// super("您[" + target + "]访问[" + uri + "]太频繁了，歇一会儿吧.");
	}

}