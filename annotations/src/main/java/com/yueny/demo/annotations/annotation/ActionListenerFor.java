package com.yueny.demo.annotations.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.yueny.demo.annotations.service.listener.ActionListener;

/**
 * 注解绑定监听器
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年2月16日 下午9:03:12
 *
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionListenerFor {
	/**
	 * @return
	 */
	Class<? extends ActionListener> listener();

}
