package com.yueny.demo.frequence.sla.limit.manager;

import java.util.Observable;
import java.util.Random;

import org.apache.commons.math3.random.RandomGenerator;
import org.apache.commons.math3.random.RandomGeneratorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.yueny.demo.frequence.sla.config.RateLimitConfig;
import com.yueny.demo.frequence.sla.config.RateLimitConfigEntry;
import com.yueny.demo.frequence.sla.limit.bucket.DefaultRateLimitBucket;
import com.yueny.demo.frequence.sla.limit.bucket.RateLimitBucket;

import lombok.Setter;

/**
 * 离散计数的限流管理器
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月16日 下午1:43:59
 *
 */
public class DiscreteRateLimitManager implements FrequencyManager, InitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(DiscreteRateLimitManager.class);

	private DefaultRateLimitBucket[] buckets = new DefaultRateLimitBucket[] { new DefaultRateLimitBucket(null, 0, 50) };

	private RandomGenerator randomGenerator = null;
	/**
	 * 具体限流器配置
	 */
	@Setter
	private RateLimitConfig rateLimitConfig;
	/**
	 * 要获取的配置项键名
	 */
	@Setter
	private String slaKey;

	/**
	 * 初始化限流管理器，此处是初始化随机数生成器
	 */
	public DiscreteRateLimitManager() {
		randomGenerator = RandomGeneratorFactory.createRandomGenerator(new Random(System.currentTimeMillis()));
	}

	/**
	 * 执行桶的初始化
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		init();
	}

	/**
	 * 从限流器中获取一个计算桶，选择算法由实现确定 一开始就把内部的桶都取出待用，避免使用过程中做桶初始化造成问题。
	 *
	 * @return 限流计算桶
	 */
	@Override
	public RateLimitBucket getBucket() {
		if (buckets == null || buckets.length == 0) {
			logger.warn("The buckets of {} have not initialized！", slaKey);
			return null;
		}

		final RateLimitBucket[] buckets = this.buckets;
		int bucketIndex = 0;
		final int count = buckets.length;
		if (count > 1) {
			bucketIndex = this.randomGenerator.nextInt(count);
		}
		logger.debug("Getting bucket {}/{} of {}, threshold is {}.", bucketIndex, count, slaKey,
				buckets[bucketIndex].getThreshold());

		return buckets[bucketIndex];
	}

	/**
	 * 初始化限流器
	 */
	@Override
	public void init() {
		Assert.hasText(slaKey, "SLA_KEY is empty!!");
		Assert.notNull(rateLimitConfig, "rateLimitConfig should NOT be null!!");

		final RateLimitConfigEntry config = rateLimitConfig.getConfigEntry(slaKey);
		if (config != null) {
			config.addObserver(this);
			initBuckets(config);
		} else {
			logger.warn("The configuration of {} is NOT found, cancel the initialization.", slaKey);
		}
	}

	/**
	 * 根据细节配置返回是否开启本限流器
	 *
	 * @return 是否开启
	 */
	@Override
	public boolean isEnable() {
		final RateLimitConfigEntry entry = rateLimitConfig.getConfigEntry(slaKey);
		return entry == null ? false : entry.isEnable();
	}

	/**
	 * 如果配置发生变化，重新初始化
	 *
	 * @param o
	 *            观察者模式对应的观察对象
	 * @param arg
	 *            传送的参数
	 */
	@Override
	public void update(final Observable o, final Object arg) {
		init();
	}

	/**
	 * 初始化计算桶，不删除原桶，直接更换引用指向
	 *
	 * @param config
	 *            限流配置信息
	 */
	private void initBuckets(final RateLimitConfigEntry config) {
		int bucketCount = config.getThreshold() / RateLimitBucket.DEFAULT_THRESHOLD;
		if (bucketCount == 0) {
			bucketCount = 1;
		}

		final int threshold = config.getThreshold() / bucketCount;
		logger.debug("Initialize buckets of {}, bucketCount={}, threshold={}", config.getKeyName(), bucketCount,
				threshold);

		final DefaultRateLimitBucket[] tmpBuckets = new DefaultRateLimitBucket[bucketCount];
		for (int i = 0; i < bucketCount; i++) {
			tmpBuckets[i] = new DefaultRateLimitBucket(this.slaKey, i, threshold);
		}
		this.buckets = tmpBuckets;
	}

}
