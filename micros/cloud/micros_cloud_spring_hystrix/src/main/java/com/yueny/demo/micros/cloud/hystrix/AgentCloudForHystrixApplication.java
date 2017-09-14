package com.yueny.demo.micros.cloud.hystrix;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

import lombok.extern.slf4j.Slf4j;

@ComponentScan(basePackages = { "com.yueny.demo.micros.cloud.hystrix", "com.yueny.demo.micros.cloud.spring" })
/**
 * 使用@EnableCircuitBreaker或@EnableHystrix注解开启Hystrix的使用<br>
 * 注意：这里我们还可以使用Spring Cloud应用中的@SpringCloudApplication注解来修饰应用主类，该注解的具体定义如下所示。
 * 我们可以看到该注解中包含了上我们所引用的三个注解，这也意味着一个Spring
 * Cloud标准应用应包含服务发现以及断路器[@EnableCircuitBreaker、 @EnableDiscoveryClient、 @SpringBootApplication
 * ]。
 */
@EnableCircuitBreaker
@EnableDiscoveryClient
@SpringBootApplication
@Slf4j
public class AgentCloudForHystrixApplication implements DisposableBean {
	private static Class<AgentCloudForHystrixApplication> applicationClass = AgentCloudForHystrixApplication.class;

	public static void main(final String[] args) {
		try {
			SpringApplication.run(AgentCloudForHystrixApplication.class, args);
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
