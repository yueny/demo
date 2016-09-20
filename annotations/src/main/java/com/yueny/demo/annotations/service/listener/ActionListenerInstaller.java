package com.yueny.demo.annotations.service.listener;

import java.lang.reflect.Method;

import com.yueny.demo.annotations.annotation.ActionListenerFor;

/**
 * 注解处理器
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年2月16日 下午9:07:04
 *
 */
public class ActionListenerInstaller {
	public static void install(final Object targetObject)
			throws IllegalAccessException, InstantiationException {
		for (final Method method : targetObject.getClass().getMethods()) {
			// 如果该成员变量被ActionListenerFor标记了
			if (method.isAnnotationPresent(ActionListenerFor.class)) {
				// 获取到注解中的Listener
				final ActionListenerFor annotation = method
						.getAnnotation(ActionListenerFor.class);
				final Class<? extends ActionListener> listener = annotation
						.listener();

				System.out.println(listener.newInstance());
				listener.newInstance().action();
			}
		}
	}
}
