package com.yueny.demo.micros.cloud;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年11月16日 上午9:48:39
 */
@EnableEurekaServer
@SpringBootApplication
@Slf4j
public class AgentCloudForEurekaApplication implements DisposableBean {
	public static void main(final String[] args) {
		new SpringApplicationBuilder(AgentCloudForEurekaApplication.class)
				.web(true).run(args);
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
