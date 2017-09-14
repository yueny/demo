package com.yueny.demo.micros.cloud.eureka.consumer;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年11月16日 上午9:48:39
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.yueny.demo.micros.cloud.eureka.consumer", "com.yueny.demo.micros.cloud.spring" })
// 激活Eureka中的DiscoveryClient实现，实现Controller中对服务信息的输出; 将当前应用加入到服务治理体系中
@EnableDiscoveryClient
// 开启扫描Spring Cloud Feign客户端的功能
@EnableFeignClients
@Slf4j
public class AgentCloudForEurekaConsumerApplication implements DisposableBean {
	public static void main(final String[] args) {
		new SpringApplicationBuilder(AgentCloudForEurekaConsumerApplication.class).web(true).run(args);
	}

	/*
	 * Application exit
	 *
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	@Override
	public void destroy() throws Exception {
		log.error("服务关闭了~~~");
	}

}
