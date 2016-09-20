package com.yueny.demo.frequence.sla.limit.strategy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yueny.demo.frequence.sla.config.RateLimitConfig;
import com.yueny.demo.frequence.sla.config.parser.ISlaRateLimitParser;
import com.yueny.demo.frequence.sla.limit.manager.DiscreteRateLimitManager;
import com.yueny.demo.frequence.sla.limit.manager.FrequencyManager;

import lombok.Getter;
import lombok.Setter;

/**
 * 内存 频率限制策略(不跨JVM)
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月15日 下午9:23:39
 *
 */
public class MemoryFrequencyStrategy implements IFrequencyStrategy {
	private static final Logger logger = LoggerFactory.getLogger(MemoryFrequencyStrategy.class);

	private final ReentrantLock lock = new ReentrantLock();
	private final Map<String, FrequencyManager> managerMap = new ConcurrentHashMap<String, FrequencyManager>();
	@Getter
	@Setter
	private RateLimitConfig rateLimitConfig = new RateLimitConfig();

	/**
	 * 查找具体的限流管理器
	 *
	 * @param slaKey
	 *            根据给定的键名进行查找
	 * @param slaRateLimitParser
	 *            解释器
	 * @return 找到的限流管理器，如果slaKey为空，返回null
	 */
	@Override
	public FrequencyManager getManagerBySlaKey(final String slaKey, final ISlaRateLimitParser slaRateLimitParser) {
		if (StringUtils.isBlank(slaKey)) {
			return null;
		}
		if (managerMap.containsKey(slaKey)) {
			return managerMap.get(slaKey);
		}
		try {
			lock.lock();
			if (managerMap.containsKey(slaKey)) {
				return managerMap.get(slaKey);
			}

			final FrequencyManager manager = createManager(slaKey, slaRateLimitParser);
			managerMap.put(slaKey, manager);
			return manager;
		} catch (final Exception e) {
			logger.error("Exception occurred when creating " + slaKey + " manager.", e);
		} finally {
			lock.unlock();
		}
		return null;
	}

	public boolean isEnableLimit() {
		return rateLimitConfig.isEnableLimit();
	}

	private FrequencyManager createManager(final String slaKey, final ISlaRateLimitParser slaRateLimitParser) {
		final DiscreteRateLimitManager manager = new DiscreteRateLimitManager();
		manager.setSlaKey(slaKey);
		if (slaRateLimitParser.isWholeKey(slaKey)) {
			// 全局
			manager.setRateLimitConfig(new RateLimitConfig());
		} else {
			manager.setRateLimitConfig(rateLimitConfig);
		}

		manager.init();
		logger.info("Creating a new rateLimitManager of {} - {}.", slaKey, manager.toString());
		return manager;
	}

}
