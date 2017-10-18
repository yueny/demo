package com.yueny.demo.cas.client;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年11月16日 上午9:48:39
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.yueny.demo.cas.client", "com.yueny.demo.spring.core" })
@ImportResource(locations = { "classpath:/config/spring-sla.xml" })
@Slf4j
public class AgentBootForCasClientApplication implements DisposableBean {
	public static void main(final String[] args) {
		try {
			SpringApplication.run(AgentBootForCasClientApplication.class, args);
		} catch (final Exception e) {
			log.error("服务启动异常:", e);
		}
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
