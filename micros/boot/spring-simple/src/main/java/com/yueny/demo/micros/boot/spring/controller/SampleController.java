package com.yueny.demo.micros.boot.spring.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class SampleController {
	/**
	 * 配置
	 */
	@Value("${a}")
	private String aa;

	@RequestMapping("/")
	// @GetMapping("/")
	@ResponseBody
	public String home() {
		log.info("被访问了~！");
		return "Hello World!" + aa;
	}

}
