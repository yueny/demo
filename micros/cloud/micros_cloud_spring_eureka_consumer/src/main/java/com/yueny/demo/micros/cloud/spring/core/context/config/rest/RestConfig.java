package com.yueny.demo.micros.cloud.spring.core.context.config.rest;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * REST请求
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年9月14日 下午2:52:17
 *
 */
@Configuration
public class RestConfig {
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplateForRibbon() {
		return new RestTemplate();
	}
}
