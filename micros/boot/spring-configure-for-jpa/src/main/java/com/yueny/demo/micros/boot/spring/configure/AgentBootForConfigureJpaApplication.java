package com.yueny.demo.micros.boot.spring.configure;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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
@ComponentScan(basePackages = { "com.yueny.superclub.util.sla.core", // 限流
		"com.yueny.demo.micros.boot.spring.configure" })
@ImportResource(locations = { "classpath:/config/spring-dozer.xml", "classpath:/config/spring-db.xml",
		"classpath:/config/spring-sla.xml", "classpath:/config/cfg-properties.xml" })
@EnableTransactionManagement(proxyTargetClass = true) // 启用JPA事务管理
@EnableJpaRepositories(basePackages = "com.yueny.demo.micros.boot.spring.configure.repository") // 启用JPA资源库并指定接口资源库位置
@EntityScan(basePackages = "com.yueny.demo.micros.boot.spring.configure.entry") // 定义实体位置
@EnableScheduling
@Slf4j
public class AgentBootForConfigureJpaApplication extends SpringBootServletInitializer implements DisposableBean {
	private static Class<AgentBootForConfigureJpaApplication> applicationClass = AgentBootForConfigureJpaApplication.class;

	public static void main(final String[] args) {
		try {
			SpringApplication.run(AgentBootForConfigureJpaApplication.class, args);
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
