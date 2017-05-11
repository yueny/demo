package com.yueny.demo.micros.boot.spring.context.config.db;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * JdbcTemplate创建
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年4月26日 下午4:04:05
 *
 */
// @Configuration
public class JdbcTemplateConfig {
	@Bean
	@Primary
	public JdbcTemplate primaryJdbcTemplate(final DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean(name = "quartzJdbcTemplate")
	public JdbcTemplate secondJdbcTemplate(@Qualifier("quartzDataSource") final DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}
