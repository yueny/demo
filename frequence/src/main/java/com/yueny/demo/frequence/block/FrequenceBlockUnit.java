package com.yueny.demo.frequence.block;

import org.apache.commons.lang3.time.StopWatch;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * FrequenceBlockUnit 频率控制单元(阻塞)
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月14日 下午8:03:37
 *
 */
@ToString
public class FrequenceBlockUnit {
	/**
	 * 在指定限频时间{limitSplitTime}内执行响应数量{limitCount}
	 */
	@Getter
	@Setter
	private int limitCount;
	/**
	 * 限制频率时间,默认1000毫秒(1秒)
	 */
	@Getter
	@Setter
	private Long limitSplitTime = 1000L;
	/**
	 *
	 */
	@Getter
	private int realCount = 0;
	/**
	 * 已被观察时间.StopWatch
	 */
	@Getter
	private final StopWatch watch;

	public FrequenceBlockUnit() {
		this.watch = new StopWatch();
	}

	/**
	 * realCount递归
	 */
	public void plusRealCount() {
		this.realCount++;
	}

	/**
	 * realCount归零
	 */
	public void zeroRealCount() {
		this.realCount = 0;
	}

}
