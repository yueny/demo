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
public enum ExpCycleType {

	/**
	 * 自定义cron表达式
	 */
	CRON("自定义cron表达式"),
	/**
	 * 每天
	 */
	DAY("每天"),
	/**
	 * 每月
	 */
	MONTH("每月"),
	/**
	 * 一次
	 *
	 */
	ONCE("一次"),;

	private static List<QuartzCycleBo> plainText = new ArrayList<>();
	static {
		for (final ExpCycleType type : values()) {
			final QuartzCycleBo bo = new QuartzCycleBo();
			bo.setCode(type.name());
			bo.setDesc(type.getDesc());
			plainText.add(bo);
		}
	}

	public static List<QuartzCycleBo> getPlainText() {
		return plainText;
	}

	@Getter
	private String desc;

	ExpCycleType(final String desc) {
		this.desc = desc;
	}

}
