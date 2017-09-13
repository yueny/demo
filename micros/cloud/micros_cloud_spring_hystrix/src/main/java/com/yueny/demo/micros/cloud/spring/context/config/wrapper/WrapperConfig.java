package com.yueny.demo.micros.cloud.spring.context.config.wrapper;

import java.io.IOException;

import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * Created by Rocky on 2017/4/26.
 */
/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年5月11日 上午11:11:04
 *
 */
// @Configuration
public class WrapperConfig {
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfig() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Bean(name = "mapper")
	public DozerBeanMapperFactoryBean mapper() {
		final DozerBeanMapperFactoryBean mapper = new DozerBeanMapperFactoryBean();

		final ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		try {
			mapper.setMappingFiles(resolver.getResources("classpath:/config/dozer/dozer-config.xml"));
		} catch (final IOException e) {
			e.printStackTrace();
		}

		return mapper;
	}

}
