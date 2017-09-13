/**
 *
 */
package com.yueny.demo.micros;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年5月9日 上午11:44:32
 * @since
 */
@EnableAutoConfiguration
@ComponentScan(basePackages = { "com.yueny.demo.micros.cloud", "com.yueny.kapo.core",
		"com.yueny.superclub.util.sla.core", "com.yueny.demo.common.example" })
/*
 * 需要使用基于XML的配置，在@Configuration所在的类开始。 使用附加的@ImportResource注解加载XML配置文件
 */
@ImportResource(locations = { "classpath:/config/spring-dozer.xml" })
@Configuration
@PropertySource(value = { "classpath:/properties/global.properties", "classpath:/properties/app.properties",
		"classpath:/properties/redis.properties",
		"classpath:/properties/mail.properties" }, ignoreResourceNotFound = true, encoding = "utf-8")
public class TestContextConfiguration {
	// .
}
