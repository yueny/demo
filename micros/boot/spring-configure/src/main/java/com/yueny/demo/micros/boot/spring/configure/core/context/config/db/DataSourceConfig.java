package com.yueny.demo.micros.boot.spring.configure.core.context.config.db;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
	public DataSource dataSource() {
		// DataSourceBuilder.create().type(dataSourceType).build()
		return DataSourceBuilder.create().driverClassName("").build();

		// final DruidDataSource dataSource = new DruidDataSource();
		// dataSource.setDriverClassName(SpringConstants.MYSQL_DRIVER);
		// dataSource.setUrl(url);
		// dataSource.setUsername(username);
		// dataSource.setPassword(password);
		// dataSource.setFilters("config,stat");
		// dataSource.setConnectionProperties("config.decrypt=" + decrypt);
		// dataSource.setMaxActive(100);
		// dataSource.setInitialSize(20);
		// dataSource.setMaxWait(60000);
		// dataSource.setMinIdle(1);
		// dataSource.setTimeBetweenEvictionRunsMillis(3000);
		// dataSource.setMinEvictableIdleTimeMillis(300000);
		// dataSource.setValidationQuery("SELECT 'x'");
		// dataSource.setTestWhileIdle(Boolean.TRUE);
		// dataSource.setTestOnBorrow(Boolean.FALSE);
		// dataSource.setTestOnReturn(Boolean.FALSE);
		// dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
		// return dataSource;
	}

}
