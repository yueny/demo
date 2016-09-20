package com.yueny.demo.frequence.sla.config.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * IP访问频率限制.
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
public @interface IpFrequency {

	/**
	 * 在millseconds时间内限制访问的次数，默认值100
	 */
	int ipCount() default 100;

	/**
	 * 时间段,单位为毫秒，默认1秒
	 * 
	 * @return 秒
	 */
	int millseconds() default 1000;

}
