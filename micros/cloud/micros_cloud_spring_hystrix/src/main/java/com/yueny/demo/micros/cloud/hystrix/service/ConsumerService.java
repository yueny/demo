package com.yueny.demo.micros.cloud.hystrix.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Component
public class ConsumerService {
	@Autowired
	private RestTemplate restTemplateForRibbon;

	/**
	 * @return 指定服务降级方法
	 */
	@HystrixCommand(fallbackMethod = "fallback")
	public String consumer() {
		final String url = "http://eureka-client/welcome";
		// final String url = "http://eureka-client/welcome/big";

		return restTemplateForRibbon.getForObject(url, String.class);
	}

	public String fallback() {
		return "fallback";
	}
}
