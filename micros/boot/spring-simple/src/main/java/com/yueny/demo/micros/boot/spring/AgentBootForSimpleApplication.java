package com.yueny.demo.micros.boot.spring;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年11月16日 上午9:48:39
 *
 */
@SpringBootApplication
/**
 * 配置文件
 */
@PropertySource(value = {
		"classpath:/properties/global.properties" }, ignoreResourceNotFound = true, encoding = "utf-8")
public class AgentBootForSimpleApplication {
	// extends SpringBootServletInitializer implements
	// EmbeddedServletContainerCustomizer {
	private static final Logger logger = LoggerFactory.getLogger(AgentBootForSimpleApplication.class);

	public static void main(final String[] args) {
		SpringApplication.run(AgentBootForSimpleApplication.class, args);
		// System.exit(0);
	}

	// @Override
	// public void customize(final ConfigurableEmbeddedServletContainer
	// container) {
	// container.setPort(8090);
	// }

	@PostConstruct
	public void doSomething() {
		logger.info("Sample Message");
	}

	// @Override
	// protected SpringApplicationBuilder configure(final
	// SpringApplicationBuilder builder) {
	// return builder.sources(AgentApplication.class);
	// }

}
