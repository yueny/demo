package com.yueny.demo.spring.context.config.db;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * Created by Rocky on 2017/4/27.
 */
// @Configuration
public class MybatisConfig {
	@Bean
	public MapperScannerConfigurer mapperScannerConfigure() {
		final MapperScannerConfigurer mapperScannerConfigure = new MapperScannerConfigurer();
		mapperScannerConfigure.setSqlSessionFactoryBeanName("sqlSessionFactory");
		mapperScannerConfigure.setBasePackage("com.miz.asset.dao.mapper");
		return mapperScannerConfigure;
	}

	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactoryBean sqlSessionFactory(@Qualifier("dataSource") final DataSource dataSource) {
		final SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();

		sqlSessionFactory.setDataSource(dataSource);

		final ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		sqlSessionFactory.setConfigLocation(resolver.getResource("classpath:/config/mybatis-config.xml"));
		sqlSessionFactory.setTypeAliasesPackage("com.miz.asset.entity");
		return sqlSessionFactory;
	}

}
