package com.yueny.demo.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Quick start<br>
 * 用于快速启动和关闭job服务
 *
 * @author yueny09 <deep_blue_yang@163.com>
 * @DATE 2017年3月9日 下午5:53:29
 */
@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = { "com.yueny.demo.job", "com.yueny.kapo.core", "com.yueny.demo.spring",
		"com.yueny.demo.common.example" })
@ImportResource(locations = { "classpath*:/config/demo-*.xml" })
@PropertySource({ "classpath:/properties/app.properties", "classpath:/properties/redis.properties",
		"classpath:/properties/db.properties", "classpath:/properties/job-ej.properties" })
public class JobElasticAgentBootstrap {
	/**
	 * 日志记录器
	 */
	private static final Logger logger = LoggerFactory.getLogger(JobElasticAgentBootstrap.class);

	public static void main(final String[] args) {
		try {
			SpringApplication.run(JobElasticAgentBootstrap.class, args);
		} catch (final Exception e) {
			logger.error("服务启动异常:", e);
			e.printStackTrace();
		}
	}

}