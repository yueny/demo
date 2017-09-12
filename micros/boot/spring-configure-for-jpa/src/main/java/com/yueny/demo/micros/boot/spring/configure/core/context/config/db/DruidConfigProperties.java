package com.yueny.demo.micros.boot.spring.configure.core.context.config.db;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * Druid配置<br>
 * 只对存在于spring boot yml文件中的配置生效。配置中心的配置不会被加载到。<br>
 *
 * <pre>
 * druid.config.filter.url=/*
 * </pre>
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年9月12日 下午12:55:36
 *
 */
@Component
@ConfigurationProperties(prefix = "druid")
public class DruidConfigProperties {
	@Data
	public static class DruidConfig {
		/**
		 * 过滤规则
		 */
		private String filterUrl;
		private String statView;
	}

	private DruidConfig config;

	public DruidConfig getConfig() {
		return config;
	}

	public void setConfig(final DruidConfig config) {
		this.config = config;
	}

}
