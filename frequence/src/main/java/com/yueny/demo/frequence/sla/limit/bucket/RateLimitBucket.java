package com.yueny.demo.frequence.sla.limit.bucket;

/**
 * 限流器中的计算桶 主要用来计算当前流量，判断是否限流
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月16日 下午1:37:55
 *
 */
public interface RateLimitBucket {
	/**
	 * 默认的计算桶阈值
	 */
	int DEFAULT_THRESHOLD = 50;

	/**
	 * 减少当前计算桶的值
	 *
	 * @return 减少后的值
	 */
	int decreaseCount();

	/**
	 * 获得限流键名
	 *
	 * @return 键名
	 */
	String getKey();

	/**
	 * 获取限流阈值
	 *
	 * @return 限流阈值
	 */
	int getThreshold();

	/**
	 * @return 当前流量
	 */
	int getTraffic();

	/**
	 * 增加当前计算桶的值
	 *
	 * @return 增加后的值
	 */
	int increaseCount();

	/**
	 * 是否被限流
	 *
	 * @return 判断结果
	 */
	boolean isLimit();

	/**
	 * 重置当前计算桶
	 */
	void reset();

	// /**
	// * 设置限流阈值
	// *
	// * @param threshold
	// * 限流阈值
	// */
	// void setThreshold(final int threshold);
}
