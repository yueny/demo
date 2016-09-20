package com.yueny.demo.frequence.sla.limit.advice;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import com.yueny.demo.frequence.sla.FrequencyException;
import com.yueny.demo.frequence.sla.config.RateLimitConfig;
import com.yueny.demo.frequence.sla.config.annotation.Frequency;
import com.yueny.demo.frequence.sla.config.parser.ISlaRateLimitParser;
import com.yueny.demo.frequence.sla.config.parser.WebMethodParser;
import com.yueny.demo.frequence.sla.limit.bucket.RateLimitBucket;
import com.yueny.demo.frequence.sla.limit.manager.FrequencyManager;
import com.yueny.demo.frequence.sla.limit.strategy.IFrequencyStrategy;
import com.yueny.demo.frequence.sla.limit.strategy.MemoryFrequencyStrategy;

import lombok.Setter;

/**
 * 具体的限流执行逻辑AOP实现
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月16日 下午1:19:00
 *
 */
public class RateLimitAdvice {
	private static final Logger logger = LoggerFactory.getLogger(RateLimitAdvice.class);
	@Setter
	private IFrequencyStrategy frequencyStrategy;
	@Setter
	private ISlaRateLimitParser slaRateLimitParser;

	/**
	 * 执行逻辑，判断是否限流，并计数
	 *
	 * @param pjp
	 *            AOP拦截的连接点
	 *
	 * @return 方法执行结果
	 */
	public Object process(final ProceedingJoinPoint pjp) throws Throwable {
		Method method = ((MethodSignature) pjp.getSignature()).getMethod();
		// eg. 'public long
		// com.yueny.demo.web.controller.FrequencyController.welcome(java.lang.Long)'
		method = ReflectionUtils.findMethod(pjp.getTarget().getClass(), method.getName(), method.getParameterTypes());

		if (slaRateLimitParser == null) {
			slaRateLimitParser = new WebMethodParser();
		}

		final Frequency annotation = slaRateLimitParser.getAnnotation(method);
		if (frequencyStrategy == null) {
			frequencyStrategy = new MemoryFrequencyStrategy();

			final RateLimitConfig rateLimitConfig = new RateLimitConfig();
			rateLimitConfig.setEnableLimit(!annotation.ignore());
			rateLimitConfig.setThreshold(annotation.threshold());
			frequencyStrategy.setRateLimitConfig(rateLimitConfig);
		}

		if (annotation == null) {
			logger.debug("{} has no rate limit configuration, execute directly.", method);
			return pjp.proceed();
		} else if (annotation.ignore()) {// !rateLimitBoss.isEnableLimit()
			logger.debug("The rate limit is currently disabled, execute directly.");
			return pjp.proceed();
		} else {
			return executeWithLimitConfig(pjp, method, annotation);
		}
	}

	private void checkLimit(final Frequency annotation, final List<RateLimitBucket> bucketList)
			throws FrequencyException {
		final boolean ignore = annotation.ignore();
		if (ignore) {
			return;
		}
		for (final RateLimitBucket bucket : bucketList) {
			if (bucket.isLimit()) {
				logger.warn("{} 并发请求量达到上限{}/{}, 被限流!", bucket.getKey(), bucket.getTraffic(), bucket.getThreshold());
				throw new FrequencyException(bucket.getKey());
			}
		}
	}

	private void checkLimit(final Frequency annotation, final List<RateLimitBucket> bucketList, final int[] values)
			throws FrequencyException {
		final boolean ignore = annotation.ignore();
		if (ignore) {
			return;
		}
		for (int i = 0; i < values.length; i++) {
			final RateLimitBucket bucket = bucketList.get(i);
			if (values[i] > bucket.getThreshold()) {
				logger.warn("{} 并发请求量达到上限{}/{}, 被限流!", bucket.getKey(), bucket.getTraffic(), bucket.getThreshold());
				throw new FrequencyException(bucket.getKey());
			}
		}
	}

	private void decreaseCount(final List<RateLimitBucket> bucketList) {
		for (final RateLimitBucket bucket : bucketList) {
			bucket.decreaseCount();
		}
	}

	/**
	 * 获取对应的配置键值，做两次检查，以避免极端情况下单次校验出现问题
	 */
	private Object executeWithLimitConfig(final ProceedingJoinPoint pjp, final Method method,
			final Frequency annotation) throws Throwable {
		// 请求参数值: pjp.getArgs() , eg. 9

		final String[] keys = slaRateLimitParser.parseKeys(method, pjp.getTarget().getClass(), pjp.getArgs());
		if (keys == null || keys.length == 0) {
			logger.warn("{} has no limit configuration, proceed execution directly.", method);
			return pjp.proceed();
		}

		Object retValue = null;
		final List<RateLimitBucket> bucketList = getRateLimitBuckets(keys, annotation, slaRateLimitParser);
		checkLimit(annotation, bucketList);

		final int[] values = increaseCount(bucketList);
		try {
			checkLimit(annotation, bucketList, values);
			logger.debug("{} can continue its execution.", method);
			retValue = pjp.proceed();
		} catch (final FrequencyException e) {
			throw e;
		} finally {
			decreaseCount(bucketList);
		}
		return retValue;
	}

	private List<RateLimitBucket> getRateLimitBuckets(final String[] keys, final Frequency annotation,
			final ISlaRateLimitParser slaRateLimitParser) {
		final List<RateLimitBucket> bucketList = new ArrayList<RateLimitBucket>(keys.length);
		for (final String key : keys) {
			final FrequencyManager manager = frequencyStrategy.getManagerBySlaKey(key, slaRateLimitParser);
			if (manager == null) {
				logger.debug("{} is empty, it will be ignored.", key);
				continue;
			}

			if (manager.isEnable()) {
				final RateLimitBucket bucket = manager.getBucket();
				bucketList.add(bucket);
			} else {
				logger.debug("{} is disabled, it will be ignored.", key);
			}
		}
		return bucketList;
	}

	private int[] increaseCount(final List<RateLimitBucket> bucketList) {
		final int[] values = new int[bucketList.size()];
		for (int i = 0; i < bucketList.size(); i++) {
			values[i] = bucketList.get(i).increaseCount();
		}
		return values;
	}
}
