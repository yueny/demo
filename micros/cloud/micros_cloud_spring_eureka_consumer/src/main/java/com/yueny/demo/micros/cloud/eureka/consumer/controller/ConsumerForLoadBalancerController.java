package com.yueny.demo.micros.cloud.eureka.consumer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Description("欢迎页")
public class ConsumerForLoadBalancerController {
	/**
	 * 负载均衡客户端的抽象定义
	 */
	@Autowired
	private LoadBalancerClient loadBalancerClient;
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping(value = "/consumer/balancer")
	public String balancer() {
		final ServiceInstance serviceInstance = loadBalancerClient.choose("eureka-client");
		final String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/welcome";
		logger.info("/consumer/balancer, url：{}。", url);

		return restTemplate.getForObject(url, String.class);
	}

}
