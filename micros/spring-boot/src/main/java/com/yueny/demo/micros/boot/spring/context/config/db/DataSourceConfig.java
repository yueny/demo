package com.yueny.demo.micros.boot.spring.context.config.db;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 创建数据源
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年4月26日 下午4:00:42
 *
 */
@Configuration
public class DataSourceConfig {
	/**
	 * 主数据源创建
	 */
	@Bean(name = "dataSource")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource primaryDataSource() {
		// DataSourceBuilder.create().type(dataSourceType).build()
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "quartzDataSource")
	@ConfigurationProperties(prefix = "spring.quartzDataSource")
	public DataSource secondDataSource() {
		return DataSourceBuilder.create().build();
	}

}
