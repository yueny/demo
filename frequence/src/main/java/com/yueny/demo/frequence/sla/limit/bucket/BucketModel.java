package com.yueny.demo.frequence.sla.limit.bucket;

import lombok.Getter;

/**
 * 计算桶模型
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月16日 下午8:54:58
 *
 */
public class BucketModel {
	/**
	 * 当前桶对应该键名的全部桶的编号
	 */
	@Getter
	private int index = 0;
	/**
	 * 限流键名
	 */
	@Getter
	private final String key;
	/**
	 * 限流阈值
	 */
	@Getter
	private int threshold = RateLimitBucket.DEFAULT_THRESHOLD;

	/**
	 * @param key
	 * @param index
	 * @param threshold
	 *            如果是负数或者0，直接使用默认值
	 */
	public BucketModel(final String key, final int index, final int threshold) {
		this.key = key;
		this.index = index;

		if (threshold <= 0) {
			this.threshold = RateLimitBucket.DEFAULT_THRESHOLD;
		} else {
			this.threshold = threshold;
		}
	}
}
