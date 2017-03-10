package com.yueny.demo.dynamic.scheduler.job.core.model;

import com.yueny.rapid.lang.mask.pojo.instance.AbstractMaskBo;

import lombok.Getter;
import lombok.Setter;

/**
 * 任务执行周期
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年2月7日 下午1:30:19
 *
 */
@Getter
@Setter
public class QuartzCycleBo extends AbstractMaskBo {
	/**
	 * 任务执行周期key
	 */
	private String code;

	/**
	 * 任务执行周期描述
	 */
	private String desc;
}
