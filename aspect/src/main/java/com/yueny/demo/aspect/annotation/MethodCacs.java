package com.yueny.demo.aspect.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年3月17日 下午3:48:22
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MethodCacs {
	/**
	*  
	*/
	int time();
}
