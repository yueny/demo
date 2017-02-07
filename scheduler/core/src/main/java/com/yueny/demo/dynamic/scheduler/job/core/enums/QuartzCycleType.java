package com.yueny.demo.dynamic.scheduler.job.core.enums;

import java.util.ArrayList;
import java.util.List;

import com.yueny.demo.dynamic.scheduler.job.core.model.QuartzCycleBo;

import lombok.Getter;

/**
 * 任务执行周期枚举
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年2月7日 下午1:26:53
 */
public enum QuartzCycleType {

	/**
	 * 自定义cron表达式
	 */
	CRON(0, "自定义cron表达式"),
	/**
	 * 每天
	 */
	DAY(2, "每天"),
	/**
	 * 每月
	 */
	MONTH(3, "每月"),
	/**
	 * 一次
	 *
	 */
	ONCE(1, "一次"),;

	private static List<QuartzCycleBo> list = new ArrayList<>();
	static {
		for (final QuartzCycleType type : values()) {
			final QuartzCycleBo bo = new QuartzCycleBo();
			bo.setCode(type.code);
			bo.setValue(type.value);
			list.add(bo);
		}
	}

	@Getter
	private int code;
	@Getter
	private String value;

	QuartzCycleType(final int code, final String value) {
		this.code = code;
		this.value = value;
	}

}
