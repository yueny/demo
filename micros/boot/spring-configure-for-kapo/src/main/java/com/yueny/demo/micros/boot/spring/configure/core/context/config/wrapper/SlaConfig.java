package com.yueny.demo.micros.boot.spring.configure.core.context.config.wrapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yueny.superclub.util.sla.internal.ILimitAdvice;
import com.yueny.superclub.util.sla.internal.application.RateLimitAdvice;

/**
 * 限流
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年5月17日 下午7:27:31
 *
 */
@Configuration
public class SlaConfig {
	// /**
	// * 如果限流配置项为空,则创建默认限流配置项。默认true
	// */
	// @Value("${spring.sla.limit.create.ifnull:true}")
	// private boolean createIfNull;
	// /**
	// * 限流总控开关是否可用 。默认true<br>
	// * true为可用<br>
	// * 对应SLA.LIMIT.ENABLE
	// */
	// @Value("${spring.sla.limit.enable:true}")
	// private boolean enableLimit;

	@Value("${redis.client.host}")
	private String hostName;
	@Value("${redis.client.password}")
	private String password;
	@Value("${redis.client.port}")
	private Integer port;

	@Bean(name = "rateLimitAdvice")
	public ILimitAdvice rateLimitAdvice() {
		final ILimitAdvice advice = new RateLimitAdvice();

		return advice;
	}

}
