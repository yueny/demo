package com.yueny.demo.dynamic.scheduler.job.bo;

import com.yueny.demo.dynamic.scheduler.job.core.enums.ExpCycleType;
import com.yueny.demo.dynamic.scheduler.job.core.enums.JobStatusType;
import com.yueny.rapid.lang.mask.pojo.instance.AbstractMaskBo;

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
final @EqualsAndHashCode(callSuper = false, of = { "serialVersionUID" }) public class QuartzJobsTaskBo
		extends AbstractMaskBo {

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
	private JobStatusType status;
	/**
	 * 执行周期
	 */
	private ExpCycleType type;

	/**
	 * 维护人
	 */
	private String user;

	/**
	 * 系统本身 任务状态 权重
	 *
	 * @see JobStatusType weight
	 */
	private int weight;

	public void setStatus(final JobStatusType status) {
		this.status = status;
		this.weight = status.getWeight();
	}

}
