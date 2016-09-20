package com.yueny.demo.frequence.sla.config.parser;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

import com.yueny.demo.frequence.sla.config.annotation.Frequency;
import com.yueny.superclub.api.constant.Constants;

/**
 * 注解解析器基类
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月16日 下午1:26:38
 */
public abstract class AbstractSlaRateLimitParser implements ISlaRateLimitParser {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 从Method上获取注解，取不到的话用默认的。
	 *
	 * @param method
	 *            要寻找注解的方法
	 *
	 * @return 配置的注解
	 */
	@Override
	public Frequency getAnnotation(final Method method) {
		if (method == null || !(method instanceof Method)) {
			logger.warn("本解析器用于解析Method上的注解，传入参数不正确：{}", method);
			return null;
		}

		final Frequency annotation = AnnotationUtils.findAnnotation(method, Frequency.class);
		if (annotation != null) {
			logger.debug("配置的注解，类型为{}。", annotation);
			// if (isTypeAccepted(annotation) &&
			// isReturnTypeAccepted(annotation)) {
			return annotation;
			// }
		}

		return null;
	}

	@Override
	public String getWholeKey() {
		return getKeyPrefix() + Constants.DOT + ISlaRateLimitParser.ALL_KEY;
	}

	@Override
	public boolean isWholeKey(final String slaKey) {
		return StringUtils.equals(slaKey, getWholeKey());
	}

}
