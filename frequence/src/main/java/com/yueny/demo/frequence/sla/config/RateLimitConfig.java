package com.yueny.demo.frequence.sla.config;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

/**
 * 限流配置信息
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月16日 下午1:52:18
 *
 */
public class RateLimitConfig {
	/**
	 * 如果限流配置项为空,则创建默认限流配置项
	 */
	@Getter
	@Setter
	private boolean createDefaultIfNull = true;
	/**
	 * 限流是否可用,,true为可用<br>
	 * 对应SLA.LIMIT.ENABLE
	 */
	@Getter
	@Setter
	private boolean enableLimit = true;
	private final Map<String, RateLimitConfigEntry> entries = new ConcurrentHashMap<String, RateLimitConfigEntry>();
	/**
	 * 限流阈值
	 */
	@Setter
	private int threshold = RateLimitConfigEntry.DEFAULT_THRESHOLD;

	/**
	 * 使用给定的键名创建默认配置
	 *
	 * @param key
	 *            要创建的配置项的键名
	 *
	 * @return 默认配置项
	 */
	public RateLimitConfigEntry createDefaultEntry(final String key) {
		return new RateLimitConfigEntry(key);
	}

	/**
	 * 获取具体的配置项
	 *
	 * @param key
	 *            要获取的配置项键名
	 *
	 * @return 如果key为空，返回null
	 */
	public RateLimitConfigEntry getConfigEntry(final String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}

		RateLimitConfigEntry entry = entries.get(key);
		if (entry == null && createDefaultIfNull) {
			entry = createDefaultEntry(key);
			entry.setThreshold(this.threshold);
			entries.put(key, entry);
		}
		return entry;
	}

	public Map<String, RateLimitConfigEntry> getEntries() {
		return Collections.unmodifiableMap(entries);
	}

}
