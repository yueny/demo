package com.yueny.demo.rocketmq.spring.context.config;

import java.io.IOException;

import org.dozer.spring.DozerBeanMapperFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Rocky on 2017/4/26.
 */
/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年5月11日 上午11:11:04
 *
 */
@Configuration
@Slf4j
public class WrapperConfig {

	@Bean(name = "mapper")
	public DozerBeanMapperFactoryBean mapper() {
		final DozerBeanMapperFactoryBean mapper = new DozerBeanMapperFactoryBean();

		final ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		try {
			mapper.setMappingFiles(resolver.getResources("classpath:/dozer/dozer-config.xml"));
		} catch (final IOException e) {
			log.error("exception: ", e);
		}
		return mapper;
	}
}
