/**
 *
 */
package com.yueny.demo.micros.boot.spring.configure.storager.cache;

import java.util.Arrays;
import java.util.List;

import com.google.common.base.Joiner;

/**
 * 缓存服务
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年12月23日 下午4:03:46
 * @since 1.5.5
 */
public class CacheKeyUtil {
	/**
	 * key组装
	 */
	public static String assmebledKey(final String key) {
		return assmebledKeys(Arrays.asList(key));
	}

	/**
	 * key组装
	 */
	public static String assmebledKeys(final List<String> keys) {
		final StringBuilder ks = new StringBuilder();
		ks.append("EXAMPLE");
		ks.append("CACHE_REDIS_");
		ks.append(Joiner.on("_").skipNulls().join(keys));

		return ks.toString();
	}

	/**
	 * key组装
	 */
	public static String assmebledKeys(final String... keys) {
		return assmebledKeys(Arrays.asList(keys));
	}
}
