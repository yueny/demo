package com.yueny.demo.sharding.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
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
@ComponentScan(basePackages = { "com.yueny.kapo.core", // kapo database support
		"com.yueny.demo.common.example", // example database
		"com.yueny.demo.micros.boot.spring.configure" })
@ImportResource(locations = { "classpath:/config/cfg-properties.xml", "classpath:/config/spring-*.xml" })
@EnableScheduling
@EnableTransactionManagement(proxyTargetClass = true) // 启用JPA事务管理
@Slf4j
public class ShardingApplication {
	public static void main(final String[] args) {
		try {
			SpringApplication.run(ShardingApplication.class, args);
		} catch (final Exception e) {
			log.error("服务启动异常:", e);
		}
	}

}
