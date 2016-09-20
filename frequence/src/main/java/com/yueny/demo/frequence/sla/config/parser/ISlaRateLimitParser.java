package com.yueny.demo.frequence.sla.config.parser;

import java.lang.reflect.Method;

import com.yueny.demo.frequence.sla.config.annotation.Frequency;

/**
 * Frequency注解解析器
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月16日 下午1:25:26
 *
 */
public interface ISlaRateLimitParser {
	/**
	 * 关键字中的ALL
	 */
	String ALL_KEY = "ALL";
	/**
	 * 对应配置文件前缀
	 */
	String KEY_PREFIX = "SLA.LIMIT";

	/**
	 * 从给定的Method上获得注解
	 *
	 * @param method
	 *            待查找的方法
	 *
	 * @return 给定方法上配置的SlaRateLimit注解
	 */
	Frequency getAnnotation(final Method method);

	/**
	 * 获取键名前缀
	 *
	 * @return 键名前缀
	 */
	String getKeyPrefix();

	/**
	 * 获得当前解析器的类型
	 *
	 * @return 当前解析器的类型
	 */
	Class<? extends ISlaRateLimitParser> getParserClass();

	/**
	 * 全局服务限流关键字
	 */
	String getWholeKey();

	/**
	 * 是否为全局服务限流关键字
	 * 
	 * @param slaKey
	 *            限流关键字
	 */
	boolean isWholeKey(final String slaKey);

	/**
	 * 根据注解配置获取限流配置键，可能会返回多个键,目前只会有ALl
	 *
	 * @param method
	 *            待解析的方法
	 * @param clazz
	 *            方法所处的类
	 * @param args
	 *            方法的参数
	 *
	 * @return 前缀.注解中配置的键[ALl, URI]
	 */
	String[] parseKeys(final Method method, Class<?> clazz, final Object[] args);
}
