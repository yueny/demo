package com.yueny.demo.annotations.controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

import com.yueny.demo.annotations.annotation.DemoAnnotation;
import com.yueny.demo.annotations.annotation.DemoMethodAnnotation;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年2月16日 下午8:22:34
 *
 */
public class DemoControllerTest {
	DemoController dc = new DemoController();

	@Test
	public void testMethodRun() {
		System.out.println("方法修饰!");

		// 执行被指定注解标记的方法
		for (final Method method : dc.getClass().getMethods()) {
			// 是否被某一个注解修饰
			if (method.isAnnotationPresent(DemoMethodAnnotation.class)) {
				try {
					method.invoke(dc);
					// 没有抛出异常
				} catch (IllegalAccessException | InvocationTargetException e) {
					// 获取异常的引发原因
					final Throwable cause = e.getCause();
					System.out.println("method " + method.getName()
							+ " execute error: < " + e.getCause() + " >");
				}
			}

			// Annotation[] annotations = method.getAnnotations();
			// for (final Annotation annotation : annotations) {
			// System.out.println(annotation.annotationType().getName());
			// }

			final DemoMethodAnnotation demoMethodAnnotation = method
					.getAnnotation(DemoMethodAnnotation.class);
			if (demoMethodAnnotation != null) {
				System.out.println("被修饰方法名 : " + method.getName() + ", desc : "
						+ demoMethodAnnotation.desc());
			}
		}
	}

	@Test
	public void testTypeRun() {
		System.out.println("类修饰!");

		System.out.println("类上被修饰的方法有：");
		for (final Annotation an : dc.getClass().getAnnotations()) {
			System.out.println(an);
		}

		final DemoAnnotation annote = dc.getClass().getAnnotation(
				DemoAnnotation.class);
		System.out.println("取得的注解为:" + annote);

		// 是否被某一个注解修饰
		if (dc.getClass().isAnnotationPresent(DemoAnnotation.class)) {
			System.out.println("类:" + dc.getClass().getSimpleName()
					+ " 被@DemoAnnotation修饰！");
		}
	}

}
