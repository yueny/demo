package com.yueny.demo.micros.cloud.config.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Description("欢迎页")
public class WelcomeForConfigServerController {
	@Autowired
	private DiscoveryClient discoveryClient;

	@GetMapping(value = "/")
	public String home() {
		final String services = "Services: " + discoveryClient.getServices();
		System.out.println(services);

		return services;
	}

}
