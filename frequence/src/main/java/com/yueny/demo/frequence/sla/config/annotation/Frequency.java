package com.yueny.demo.frequence.sla.config.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 访问频率限制.控制当前service服务同时处理的最高并发数为threshold. <br>
 *
 * 待处理:控制当前服务器同时处理的最高并发数为threshold; <br>
 * 待处理:控制当前service在时间段内总共处理的请求数为threshold;<br>
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月15日 下午2:48:09
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Frequency {
	/**
	 * 是否忽略限流,true为忽略
	 */
	boolean ignore() default false;

	// /**
	// * 时间段,单位为毫秒，默认1秒
	// *
	// * @return 秒
	// */
	// int millseconds() default 1000;

	/**
	 * 在millseconds时间内默认限制访问次数的阈值，默认值2
	 */
	int threshold() default 2;

}
