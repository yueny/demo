package com.yueny.demo.api;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.Sets;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Deprecated
public class ExecutorBeans {
	/**
	 * 方法上的所有注解
	 */
	@Getter
	private Set<Annotation> annotations;

	/**
	 * 方法
	 */
	@Getter
	@Setter
	private Method method;

	/**
	 * 类实例
	 */
	@Getter
	@Setter
	private Object target;

	public ExecutorBeans() {
		super();
		this.annotations = new HashSet<>();
	}

	public void setAnnotations(final Annotation[] annotations) {
		this.annotations = Sets.newHashSet(annotations);
	}

	public void setAnnotations(final Set<Annotation> annotations) {
		this.annotations = annotations;
	}

}
