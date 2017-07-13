package com.yueny.demo.micros.cloud;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年11月16日 上午9:48:39
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.yueny.demo.micros.cloud", "com.yueny.kapo.core",
		"com.yueny.superclub.util.sla.core", "com.yueny.demo.common.example" })
@ImportResource(locations = { "classpath:/config/spring-dozer.xml" })
@EnableTransactionManagement
@EnableScheduling
/**
 * 配置文件
 */
@PropertySource(value = { "classpath:/properties/global.properties", "classpath:/properties/app.properties",
		"classpath:/properties/redis.properties",
		"classpath:/properties/mail.properties" }, ignoreResourceNotFound = true, encoding = "utf-8")
@Slf4j
public class AgentCloudConfigApplication extends SpringBootServletInitializer implements DisposableBean {
	private static Class<AgentCloudConfigApplication> applicationClass = AgentCloudConfigApplication.class;

	public static void main(final String[] args) {
		try {
			SpringApplication.run(AgentCloudConfigApplication.class, args);
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

	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}

}
