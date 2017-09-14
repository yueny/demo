package com.yueny.demo.micros.cloud.spring.context.config.wrapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yueny.rapid.topic.profiler.ProfilerLogAspect;

/**
 * 限流
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年5月17日 下午7:27:31
 *
 */
@Configuration
public class ProfilerConfig {

	@Bean
	public ProfilerLogAspect profilerLogAspect() {
		final ProfilerLogAspect advice = new ProfilerLogAspect();

		return advice;
	}

}
