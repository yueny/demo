package com.yueny.demo.rocketmq.provider;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;

/**
 * 消息生产者
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年11月16日 上午9:48:39
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.yueny.demo.rocketmq", "com.yueny.kapo.core", "com.yueny.demo.common.example" })
@ImportResource(locations = { "classpath:/config/demo-properties.xml", "classpath:/config/demo-mq.xml",
		"classpath:/config/demo-redis.xml" })
@EnableTransactionManagement
@EnableScheduling
@Slf4j
public class AgentRocketmqProducerBootApplication extends SpringBootServletInitializer implements DisposableBean {
	private static Class<AgentRocketmqProducerBootApplication> applicationClass = AgentRocketmqProducerBootApplication.class;

	public static void main(final String[] args) {
		try {
			SpringApplication.run(AgentRocketmqProducerBootApplication.class, args);
		} catch (final Exception e) {
			log.error("服务启动异常:", e);
		}
	}

	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
		return application.sources(applicationClass);
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
