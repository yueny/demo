package com.yueny.demo.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yueny.demo.annotation.AuthControl;
import com.yueny.demo.api.ExecutorBean;

/**
 * 注解解析工具
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年9月18日 下午6:20:22
 *
 */
public class AnnotationManagerUtil {
	/**
	 * 占位符判断表达式
	 */
	private static String HOLDER_REGEX = "^({+})$";

	/**
	 * 获取指定文件下面的RequestMapping方法保存在mapp中
	 *
	 * @param packageName
	 * @return
	 */
	public static Map<String, ExecutorBean<? extends Annotation>> getMappingMethod(final String packageName) {
		// 存放url和ExecutorBean的对应关系
		final Map<String, ExecutorBean<? extends Annotation>> mapp = new HashMap<>();

		final Reflections reflections = new Reflections(packageName);

		/*
		 * 获取该注解上面的修饰的注解类型（该注解一定是在我们配置的包路径下）
		 */
		for (final Class<?> classes : reflections.getTypesAnnotatedWith(Controller.class)) {
			// 得到该类下面的所有方法
			for (final Method method : classes.getDeclaredMethods()) {
				// 1 RequestMapping
				final ExecutorBean<RequestMapping> executorRequestBean = assemblyExecutor(classes, method,
						RequestMapping.class);
				if (executorRequestBean != null) {
					for (final String v : executorRequestBean.getAnnotationForWebBind().value()) {
						mapp.put(v, executorRequestBean);
					}
					continue;
				}

				// 2 GetMapping
				final ExecutorBean<GetMapping> executorGetBean = assemblyExecutor(classes, method, GetMapping.class);
				if (executorGetBean != null) {
					for (final String v : executorGetBean.getAnnotationForWebBind().value()) {
						mapp.put(v, executorGetBean);
					}
					continue;
				}

				// 3

			}
		}

		return mapp;
	}

	/**
	 * 获取指定文件下面的RequestMapping方法保存在mapp中<br>
	 * 存在占位符替换
	 *
	 * @param packageName
	 * @return
	 */
	public static Map<String, ExecutorBean<? extends Annotation>> getMappingMethodForHolder(final String packageName) {
		final Map<String, ExecutorBean<? extends Annotation>> mapAll = getMappingMethod(packageName);

		// 存放url和ExecutorBean的对应关系
		final Map<String, ExecutorBean<? extends Annotation>> mapp = new HashMap<>();
		for (final String key : mapAll.keySet()) {
			if (StringUtils.contains(key, "/{") && StringUtils.contains(key, "}")) {
				final String newKey = StringUtils.replacePattern(key, HOLDER_REGEX, "*");
				mapp.put(newKey, mapAll.get(key));
			} else {
				mapp.put(key, mapAll.get(key));
			}
		}
		return mapp;
	}

	// /**
	// * 获取指定文件下面的RequestMapping方法
	// *
	// */
	// private static <T extends Annotation> ExecutorBeans
	// assemblyExecutor(final Class<?> classes, final Method method) {
	// // 得到该类下面的 T 注解
	// final Annotation[] ts = method.getAnnotations();
	// if (ts == null) {
	// return null;
	// }
	//
	// final ExecutorBeans executorBeans = new ExecutorBeans();
	// executorBeans.setAnnotations(ts);
	//
	// try {
	// executorBeans.setTarget(classes.newInstance());
	// } catch (final InstantiationException e) {
	// e.printStackTrace();
	// } catch (final IllegalAccessException e) {
	// e.printStackTrace();
	// }
	// executorBeans.setMethod(method);
	//
	// return executorBeans;
	// }

	/**
	 * @param classes
	 * @param method
	 *            方法
	 * @param annotationClazz
	 *            注解类型
	 */
	private static <T extends Annotation> ExecutorBean<T> assemblyExecutor(final Class<?> classes, final Method method,
			final Class<T> annotationClazz) {
		// 得到该类下面的 T 注解
		final T t = method.getAnnotation(annotationClazz);
		if (t == null) {
			return null;
		}

		final ExecutorBean<T> executorBean = new ExecutorBean<>();
		try {
			executorBean.setTarget(classes.newInstance());
		} catch (final InstantiationException e) {
			e.printStackTrace();
		} catch (final IllegalAccessException e) {
			e.printStackTrace();
		}
		executorBean.setMethod(method);
		executorBean.setAnnotationForWebBind(t);

		final AuthControl control = method.getAnnotation(AuthControl.class);
		if (control != null) {
			executorBean.setControl(control);
		}

		return executorBean;
	}

}
