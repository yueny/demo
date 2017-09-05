package com.yueny.demo.micros.boot.spring.configure.core.context;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "service", ignoreUnknownFields = false)
public class WelcomeProperties {
	@Getter
	@Setter
	private String name;

}
