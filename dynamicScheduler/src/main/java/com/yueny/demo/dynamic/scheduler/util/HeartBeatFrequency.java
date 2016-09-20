package com.yueny.demo.dynamic.scheduler.util;

import lombok.Getter;

/**
 * 心跳检测频率
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月28日 下午11:40:21
 *
 */
public enum HeartBeatFrequency {
	/**
	 * 5秒
	 */
	FIVE(5, "0/5 * * * * ?"),
	/**
	 * 60秒
	 */
	SIXTY(60, "0 0/1 * * * ?"),
	/**
	 * 10秒
	 */
	TEN(10, "0/10 * * * * ?"),
	/**
	 * 30秒
	 */
	THIRTY(30, "0/30 * * * * ?"),
	/**
	 * 20秒
	 */
	TWENTY(20, "0/20 * * * * ?");

	@Getter
	private String cronExpression;
	@Getter
	private int seconds;

	private HeartBeatFrequency(final int seconds, final String cronExpression) {
		this.seconds = seconds;
		this.cronExpression = cronExpression;
	}

	/**
	 * @return second -> milli second
	 */
	public int getMilliSeconds() {
		return seconds * 1000;
	}

	public String getValue() {
		return name();
	}

}