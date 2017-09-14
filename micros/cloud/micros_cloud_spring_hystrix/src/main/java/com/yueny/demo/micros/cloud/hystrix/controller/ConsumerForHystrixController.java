package com.yueny.demo.micros.cloud.hystrix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yueny.demo.micros.cloud.hystrix.service.ConsumerService;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年9月14日 下午3:24:31
 *
 */
@RestController
public class ConsumerForHystrixController {
	@Autowired
	private ConsumerService consumerService;

	@GetMapping("/consumer/hystrix")
	public String feign() {
		return consumerService.consumer();
	}

}
