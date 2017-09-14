package com.yueny.demo.micros.cloud.spring.context.config.wrapper;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * 配置中心配置
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年5月27日 下午5:32:49
 *
 */
@Configuration
@Slf4j
public class DiamondConfigurerListener implements InitializingBean {
	/**
	 * dataId
	 */
	@Value("${app.system.dataId:}")
	private String dataId;
	/**
	 * group
	 */
	@Value("${app.system.group:DEFAULT_GROUP}")
	private String group;
	/**
	 * profile环境
	 */
	@Value("${app.system.code:dev}")
	private String profile;

	@Override
	public void afterPropertiesSet() throws Exception {

	}

}
