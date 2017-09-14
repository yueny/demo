package com.yueny.demo.micros.cloud.eureka.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Feign的客户端接口定义
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年9月14日 下午3:24:31
 *
 */
@RestController
public class ConsumerForFeignController {
	@Autowired
	private ConsumerForFeignClient dcClient;

	@GetMapping("/consumer/feign")
	public String feign() {
		return dcClient.consumer();
	}

}
