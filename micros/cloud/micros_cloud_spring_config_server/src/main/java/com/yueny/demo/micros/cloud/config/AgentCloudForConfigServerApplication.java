package com.yueny.demo.micros.cloud.config;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年11月16日 上午9:48:39
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigServer
public class AgentCloudForConfigServerApplication implements DisposableBean {
	public static void main(final String[] args) {
		try {
			SpringApplication.run(AgentCloudForConfigServerApplication.class, args);
		} catch (final Exception e) {
			System.out.println("服务启动异常");
		}
	}

	/*
	 * Application exit
	 *
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	@Override
	public void destroy() throws Exception {
		System.out.println("服务关闭了~~~");
	}

}
