package com.yueny.demo.micros.boot.spring.configure.core.context.config.db;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;

/**
 * 创建数据源
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年4月26日 下午4:00:42
 *
 */
// @Configuration
@Deprecated
public class DataSourceConfig {
	/**
	 * 主数据源创建
	 */
	@Bean(name = "dataSource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

}
