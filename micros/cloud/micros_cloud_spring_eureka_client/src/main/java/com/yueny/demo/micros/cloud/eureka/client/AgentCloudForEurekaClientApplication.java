package com.yueny.demo.micros.cloud.eureka.client;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年11月16日 上午9:48:39
 */
@SpringBootApplication
// 激活Eureka中的DiscoveryClient实现，实现Controller中对服务信息的输出; 将当前应用加入到服务治理体系中
@EnableDiscoveryClient
@Slf4j
public class AgentCloudForEurekaClientApplication implements DisposableBean {
	public static void main(final String[] args) {
		new SpringApplicationBuilder(AgentCloudForEurekaClientApplication.class).web(true).run(args);
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
