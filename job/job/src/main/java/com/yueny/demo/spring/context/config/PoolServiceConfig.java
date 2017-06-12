package com.yueny.demo.spring.context.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yueny.superclub.util.exec.multi.MultiThreadSupport;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年6月12日 下午1:40:12
 *
 */
@Configuration
@Slf4j
public class PoolServiceConfig {
	@Bean(name = "multiThreadSupport")
	public MultiThreadSupport multiThreadSupport() {
		return new MultiThreadSupport();
	}

}
