package com.yueny.demo.util;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.junit.Test;
import org.reflections.util.ClasspathHelper;

import com.yueny.demo.api.ExecutorBean;

public class AnnotationManageUtilTest {
	@Test
	public void testGetMappingMethod() {
		final Map<String, ExecutorBean<? extends Annotation>> mmap = AnnotationManagerUtil
				.getMappingMethod("com.yueny.demo.cas.client.controller");
		System.out.println("注解集合：" + mmap);

		System.out.println("根目录下的资源路径：" + ClasspathHelper.forResource("\\"));
	}

	@Test
	public void testGetMappingMethodForHolder() {
		final Map<String, ExecutorBean<? extends Annotation>> mmap = AnnotationManagerUtil
				.getMappingMethodForHolder("com.yueny.demo.cas.client.controller");
		System.out.println("注解集合：" + mmap);
	}

}
