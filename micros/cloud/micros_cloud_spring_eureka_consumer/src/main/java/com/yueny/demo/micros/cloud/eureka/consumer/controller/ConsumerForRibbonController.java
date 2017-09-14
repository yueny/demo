package com.yueny.demo.micros.cloud.eureka.consumer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Description("欢迎页")
public class ConsumerForRibbonController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RestTemplate restTemplateForRibbon;

	@GetMapping(value = "/consumer/ribbon")
	public String ribbon() {
		final String url = "http://eureka-client/welcome";
		logger.info("/consumer/ribbon, url：{}。", url);

		return restTemplateForRibbon.getForObject(url, String.class);
	}

}
