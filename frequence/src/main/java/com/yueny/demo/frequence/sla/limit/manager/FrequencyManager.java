package com.yueny.demo.frequence.sla.limit.manager;

import java.util.Observer;

import com.yueny.demo.frequence.sla.config.RateLimitConfig;
import com.yueny.demo.frequence.sla.limit.bucket.RateLimitBucket;

/**
 * 限流器接口 限流器的主要作用是用来计算当前流量信息，判断是否限流
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月16日 下午1:36:02
 *
 */
public interface FrequencyManager extends Observer {
	/**
	 * 从限流器中获取一个计算桶，选择算法由实现确定
	 *
	 * @return 限流计算桶
	 */
	RateLimitBucket getBucket();

	/**
	 * 初始化限流器
	 */
	void init();

	/**
	 * 本限流器是否开启
	 *
	 * @return 是否开启
	 */
	boolean isEnable();

	/**
	 * 设置具体限流器配置
	 *
	 * @param rateLimitConfig
	 *            限流配置信息
	 */
	void setRateLimitConfig(final RateLimitConfig rateLimitConfig);

}
