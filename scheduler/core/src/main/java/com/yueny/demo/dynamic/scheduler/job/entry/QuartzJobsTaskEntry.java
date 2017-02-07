package com.yueny.demo.dynamic.scheduler.job.entry;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.yueny.demo.dynamic.scheduler.job.core.enums.QuartzCycleType;
import com.yueny.demo.dynamic.scheduler.job.core.enums.QuartzTaskStatus;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年2月7日 下午1:38:57
 *
 */
@Getter
@Setter
public class QuartzJobsTaskEntry implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 3512287530374354180L;

	/**
	 * 任务配置id
	 */
	private long configId;

	/**
	 * 任务描述
	 */
	private String description;

	/**
	 * 执行表达式
	 */
	private String expression;

	/**
	 * 任务标识
	 */
	private String identifier;

	/**
	 * 任务参数
	 */
	private String params;

	/**
	 * 任务状态
	 * {@link com.yueny.demo.dynamic.scheduler.job.core.enums.QuartzTaskStatus}
	 */
	@Enumerated(EnumType.STRING)
	private QuartzTaskStatus status;

	/**
	 * 执行周期类型
	 * {@link com.yueny.demo.dynamic.scheduler.job.core.enums.QuartzCycleType}
	 */
	@Enumerated(EnumType.STRING)
	private QuartzCycleType type;

	/**
	 * 维护人
	 */
	private String user;

	/**
	 * 权重
	 */
	private int weight;
}
