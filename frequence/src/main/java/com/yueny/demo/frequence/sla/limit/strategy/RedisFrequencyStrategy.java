package com.yueny.demo.frequence.sla.limit.strategy;

import com.yueny.demo.frequence.sla.config.RateLimitConfig;
import com.yueny.demo.frequence.sla.config.parser.ISlaRateLimitParser;
import com.yueny.demo.frequence.sla.limit.manager.FrequencyManager;

/**
 * redis 频率限制策略
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月15日 下午9:23:12
 *
 */
public class RedisFrequencyStrategy implements IFrequencyStrategy {

	// private static Redis redis;

	@Override
	public FrequencyManager getManagerBySlaKey(final String slaKey, final ISlaRateLimitParser slaRateLimitParser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRateLimitConfig(final RateLimitConfig rateLimitConfig) {
		// TODO Auto-generated method stub

	}

	// @Override
	// public void expire(final String key, final int seconds) {
	// // getRedis().expire(key, seconds);
	// }

	// @Override
	// public boolean set(final String key) {
	// // Long result = getRedis().setnx(key, "");
	// // return (result > 0);
	// return false;
	// }

}
