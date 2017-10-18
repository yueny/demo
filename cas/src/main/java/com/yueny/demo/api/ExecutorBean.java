package com.yueny.demo.api;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.yueny.demo.annotation.AuthControl;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class ExecutorBean<T extends Annotation> {
	/**
	 * 方法上的 spring mvc bind 注解
	 */
	@Getter
	@Setter
	private T annotationForWebBind;
	/**
	 * 方法上的权限注解信息
	 */
	@Getter
	@Setter
	private AuthControl control;
	/**
	 * 方法
	 */
	private Method method;

	/**
	 * 类实例
	 */
	@Getter
	@Setter
	private Object target;

	public Method getMethod() {
		return method;
	}

	public void setMethod(final Method method) {
		this.method = method;
	}

}
