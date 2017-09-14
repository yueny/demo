package com.yueny.demo.micros.cloud.eureka.consumer.controller;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Feign的客户端接口定义<br>
 * 通过@FeignClient定义的接口来统一的生命我们需要依赖的微服务接口
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年9月14日 下午3:24:31
 *
 */
@FeignClient("eureka-client")
@Description("Feign客户端")
public interface ConsumerForFeignClient {
	@GetMapping("/welcome")
	String consumer();

}
