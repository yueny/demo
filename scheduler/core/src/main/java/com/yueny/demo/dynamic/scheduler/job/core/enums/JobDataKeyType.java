package com.yueny.demo.dynamic.scheduler.job.core.enums;

import com.yueny.superclub.api.enums.core.IEnumType;

import lombok.Getter;

/**
 * quartz 携带的数据的key
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年2月23日 下午4:34:45
 * @since
 */
public enum JobDataKeyType implements IEnumType {
	/**
	 * 数据实体key
	 */
	JOB_DATA_KEY("data"),;

	/**
	 * key
	 */
	@Getter
	private String key;

	JobDataKeyType(final String key) {
		this.key = key;
	}

}
