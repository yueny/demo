package com.yueny.demo.dynamic.scheduler.job.core.model;

import com.yueny.superclub.api.pojo.instance.AbstractBo;

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
public class QuartzCycleBo extends AbstractBo {
	/**
	 *
	 */
	private static final long serialVersionUID = -1297803106905708828L;

	private int code;

	private String value;
}
