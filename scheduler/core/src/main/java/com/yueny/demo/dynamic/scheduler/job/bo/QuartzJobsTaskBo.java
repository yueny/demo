package com.yueny.demo.dynamic.scheduler.job.bo;

import com.yueny.demo.dynamic.scheduler.job.core.enums.QuartzCycleType;
import com.yueny.demo.dynamic.scheduler.job.core.enums.QuartzTaskStatus;
import com.yueny.superclub.api.pojo.instance.AbstractBo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by chars on 2017/1/10 10:07
 *
 * @since 1.2.0
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = false, of = { "serialVersionUID" })
public class QuartzJobsTaskBo extends AbstractBo {
	/**
	 *
	 */
	private static final long serialVersionUID = -1586102203484369286L;

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
	 * 任务配置名称
	 */
	private String name;

	/**
	 * 下次执行时间
	 */
	private String nextFireTime;

	private String params;

	/**
	 * 上次执行时间
	 */
	private String previousFireTime;

	/**
	 * quartz 数据库里面 任务状态
	 */
	private String state;

	/**
	 * 系统本身 任务状态
	 */
	private QuartzTaskStatus status;
	/**
	 * 执行周期
	 */
	private QuartzCycleType type;

	/**
	 * 维护人
	 */
	private String user;

	/**
	 * 系统本身 任务状态 权重
	 *
	 * @see QuartzTaskStatus weight
	 */
	private int weight;

	public void setStatus(final QuartzTaskStatus status) {
		this.status = status;
		this.weight = status.getWeight();
	}

}
