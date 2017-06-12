package com.yueny.demo.spring.context.config.db;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * Created by Rocky on 2017/4/26.
 */
// @Configuration
public class DataSourceConfig {
	@Value("${config.decrypt}")
	private boolean decrypt;
	@Value("${mysql.password}")
	private String password;
	@Value("${mysql.url}")
	private String url;
	@Value("${mysql.username}")
	private String username;

	@Bean(name = "dataSource", destroyMethod = "close")
	public DataSource dataSource() throws Exception {
		final DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl(url);
		dataSource.setUsername(username);
		dataSource.setPassword(password);
		dataSource.setFilters("config,stat");
		dataSource.setConnectionProperties("config.decrypt=" + decrypt);
		dataSource.setMaxActive(100);
		dataSource.setInitialSize(20);
		dataSource.setMaxWait(60000);
		dataSource.setMinIdle(1);
		dataSource.setTimeBetweenEvictionRunsMillis(3000);
		dataSource.setMinEvictableIdleTimeMillis(300000);
		dataSource.setValidationQuery("SELECT 'x'");
		dataSource.setTestWhileIdle(Boolean.TRUE);
		dataSource.setTestOnBorrow(Boolean.FALSE);
		dataSource.setTestOnReturn(Boolean.FALSE);
		dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
		return dataSource;
	}

	@Bean(name = "transactionManager")
	public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") final DataSource dataSource) {
		final DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
		transactionManager.setDataSource(dataSource);
		return transactionManager;
	}

}
