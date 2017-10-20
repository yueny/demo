package com.yueny.demo.micros.cloud.config.client;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年11月16日 上午9:48:39
 */
@SpringBootApplication
public class AgentCloudForConfigClientApplication extends SpringBootServletInitializer implements DisposableBean {
	public static void main(final String[] args) {
		try {
			SpringApplication.run(AgentCloudForConfigClientApplication.class, args);
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.boot.web.support.SpringBootServletInitializer#
	 * configure(org.springframework.boot.builder.SpringApplicationBuilder)
	 */
	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
		return application.sources(AgentCloudForConfigClientApplication.class);
	}

}
