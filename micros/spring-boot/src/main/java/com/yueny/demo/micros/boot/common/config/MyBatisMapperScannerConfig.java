package com.yueny.demo.micros.boot.common.config;

import java.lang.annotation.Annotation;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 扫描MyBatis的Mapper接口
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年4月24日 下午3:03:36
 *
 */
@Configuration
/*
 * 这个配置一定要注意@AutoConfigureAfter(MyBatisConfig.class)，必须有这个配置，否则会有异常。
 * 原因就是这个类执行的比较早，由于sqlSessionFactory还不存在，后续执行出错
 */
@AutoConfigureAfter(MyBatisConfigurer.class)
public class MyBatisMapperScannerConfig {
	/**
	 * annotationClazz
	 */
	private final String annotation = "org.springframework.stereotype.Repository";
	/**
	 * mapper包路径
	 */
	private final String basePackage = "com.yueny.kapo.core;com.yueny.demo.job.dao;com.yueny.demo.common.example.dao;";
	/**
	 *
	 */
	private final String markerInterface = "com.yueny.kapo.api.biz.ISqlMapper";

	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() throws ClassNotFoundException {
		final MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		mapperScannerConfigurer.setBasePackage(basePackage);

		@SuppressWarnings("unchecked")
		final Class<? extends Annotation> annotationClass = (Class<? extends Annotation>) Class.forName(annotation);
		mapperScannerConfigurer.setAnnotationClass(annotationClass);

		final Class<?> superClass = Class.forName(markerInterface);
		mapperScannerConfigurer.setMarkerInterface(superClass);
		return mapperScannerConfigurer;
	}

}
