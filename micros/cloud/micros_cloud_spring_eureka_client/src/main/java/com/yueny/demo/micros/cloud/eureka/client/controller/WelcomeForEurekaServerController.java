package com.yueny.demo.micros.cloud.eureka.client.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Description("欢迎页")
public class WelcomeForEurekaServerController {
	@Autowired
	private DiscoveryClient discoveryClient;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping(value = "/welcome")
	public String welcome() {
		final List<String> services = discoveryClient.getServices();
		final ServiceInstance instance = discoveryClient.getLocalServiceInstance();
		logger.info("/welcome, Services：{},host:" + instance.getHost() + ", service_id:" + instance.getServiceId(),
				services);
		return "welcome";
	}

	@GetMapping(value = "/welcome/big")
	public String welcomeBig() {
		try {
			Thread.sleep(2000L);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}

		final List<String> services = discoveryClient.getServices();
		final ServiceInstance instance = discoveryClient.getLocalServiceInstance();
		logger.info("/welcome, Services：{},host:" + instance.getHost() + ", service_id:" + instance.getServiceId(),
				services);
		return "welcome";
	}

}
