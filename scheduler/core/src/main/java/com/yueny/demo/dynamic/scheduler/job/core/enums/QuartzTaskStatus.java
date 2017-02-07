package com.yueny.demo.dynamic.scheduler.job.core.enums;

import lombok.Getter;

/**
 * quartz 任务 状态
 * 
 * <p>
 * 主要匹配 TriggerState 多一个任务过期状态
 * 
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年2月7日 下午1:26:53
 * @see org.quartz.Trigger.TriggerState
 *
 */
public enum QuartzTaskStatus {

	/**
	 * 阻塞
	 */
	BLOCKED(6),

	/**
	 * 周期性的已完成, 存在下次任务调度周期
	 */
	COMPLETE(8),

	/**
	 * 错误
	 */
	ERROR(4),

	/**
	 * 任务
	 */
	EXPIRED(0),

	/**
	 * 不存在
	 */
	NONE(2),

	/**
	 * 正常
	 */
	NORMAL(12),

	/**
	 * 暂停
	 */
	PAUSED(10);

	/**
	 * 权重
	 */
	@Getter
	private int weight;

	QuartzTaskStatus(final int weight) {
		this.weight = weight;
	}
}
