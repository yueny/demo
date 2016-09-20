package com.yueny.demo.frequence.sla.limit.bucket;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 默认限流计算桶，使用AtomicInteger进行计数
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月16日 下午1:38:39
 *
 */
public class DefaultRateLimitBucket implements RateLimitBucket {
	private static final Logger logger = LoggerFactory.getLogger(DefaultRateLimitBucket.class);
	/**
	 * 计算桶模型
	 */
	private final BucketModel bucketModel;
	/**
	 * 计算器
	 */
	private final AtomicInteger counter = new AtomicInteger(0);

	/**
	 * 构造方法，设置成员属性值
	 *
	 * @param key
	 *            限流键名
	 * @param index
	 *            当前桶对应该键名的全部桶的编号
	 * @param threshold
	 *            限流阈值
	 */
	public DefaultRateLimitBucket(final String key, final int index, final int threshold) {
		bucketModel = new BucketModel(key, index, threshold);
	}

	/**
	 * 减少当前计算桶的值，有可能会减成负数，减完会做一个判断。
	 *
	 * @return 减少后的值
	 */
	@Override
	public int decreaseCount() {
		final int value = counter.decrementAndGet();
		if (value < 0) {
			counter.compareAndSet(value, 0);
			return 0;
		}
		logger.debug("decrease bucket 桶/限流键名/hash {}/{}({}) 流量 counter to {}", bucketModel.getIndex(), getKey(),
				hashCode(), value);
		return value;
	}

	@Override
	public String getKey() {
		return bucketModel.getKey();
	}

	@Override
	public int getThreshold() {
		return bucketModel.getThreshold();
	}

	@Override
	public int getTraffic() {
		return counter.get();
	}

	/**
	 * 增加当前计算桶的值
	 *
	 * @return 增加后的值
	 */
	@Override
	public int increaseCount() {
		final int value = counter.incrementAndGet();
		logger.debug("increase bucket 桶/限流键名/hash {}/{}({}) 流量 counter to {}", bucketModel.getIndex(), getKey(),
				hashCode(), value);
		return value;
	}

	/**
	 * 是否被限流，即当前计数器的值是否大于阈值
	 *
	 * @return 判断结果
	 */
	@Override
	public boolean isLimit() {
		final int value = counter.get();
		final boolean result = value > bucketModel.getThreshold();
		logger.debug("checking limit - current 桶/限流键名 counter of {}/{}: ,流量/阈值 {}/{}", bucketModel.getIndex(), getKey(),
				value, getThreshold());
		return result;
	}

	/**
	 * 重置当前计算桶，把计数器归0，有可能造成并发操作丢失，减成负数等情况，减时做了处理
	 */
	@Override
	public void reset() {
		counter.set(0);
	}

	// /**
	// * 设置限流阈值
	// *
	// * @param threshold
	// * 如果是负数或者0，直接使用默认值
	// */
	// @Override
	// public void setThreshold(final int threshold) {
	// if (threshold <= 0) {
	// logger.warn("The threshold {} is NOT accepted, using default value {}.",
	// threshold, DEFAULT_THRESHOLD);
	// bucketModel.getThreshold() = DEFAULT_THRESHOLD;
	// } else {
	// this.threshold = threshold;
	// }
	// }

}
