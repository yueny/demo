package com.yueny.demo.micros.cloud.eureka.controller;

import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Description("欢迎页")
public class WelcomeForEurekaServerController {
	@GetMapping(value = "/welcome")
	public String home() {
		return "welcome";
	}

}
