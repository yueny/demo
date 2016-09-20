package com.yueny.demo.frequence.sla.limit.strategy;

import com.yueny.demo.frequence.sla.config.RateLimitConfig;
import com.yueny.demo.frequence.sla.config.parser.ISlaRateLimitParser;
import com.yueny.demo.frequence.sla.limit.manager.FrequencyManager;

/**
 * 频率限制策略(rateLimitBoss)
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月15日 下午9:20:18
 *
 */
public interface IFrequencyStrategy {
	// /**
	// * 过期时间设置
	// */
	// void expire(String key, int seconds);
	//
	// /**
	// * 访问频率保存
	// */
	// boolean set(String key);

	/**
	 * 查找具体的限流管理器
	 *
	 * @param slaKey
	 *            根据给定的键名进行查找
	 * @param slaRateLimitParser
	 *            解释器
	 * @return 找到的限流管理器，如果slaKey为空，返回null
	 */
	FrequencyManager getManagerBySlaKey(final String slaKey, ISlaRateLimitParser slaRateLimitParser);

	/**
	 * 设置限流配置
	 *
	 * @param rateLimitConfig
	 *            限流配置
	 */
	void setRateLimitConfig(RateLimitConfig rateLimitConfig);

}
