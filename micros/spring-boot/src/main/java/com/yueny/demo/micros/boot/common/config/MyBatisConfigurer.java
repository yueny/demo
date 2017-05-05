package com.yueny.demo.micros.boot.common.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

/**
 * MyBatis基础配置
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年4月24日 下午3:02:12
 *
 */
@Configuration
@EnableTransactionManagement // 支持注解事务
// @ImportResource("classpath:applicationContext-mybatis.xml")
public class MyBatisConfigurer implements TransactionManagementConfigurer {
	@Autowired
	private DataSource dataSource;

	@Bean
	@Override
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactoryBean() {
		final SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
		bean.setDataSource(dataSource);

		// //分页插件
		// PageHelper pageHelper = new PageHelper();
		// Properties properties = new Properties();
		// properties.setProperty("reasonable", "true");
		// properties.setProperty("supportMethodsArguments", "true");
		// properties.setProperty("returnPageInfo", "check");
		// properties.setProperty("params", "count=countSql");
		// pageHelper.setProperties(properties);
		//
		// //添加插件
		// bean.setPlugins(new Interceptor[]{pageHelper});

		// 添加XML目录
		final ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		try {
			bean.setConfigLocation(resolver.getResource("classpath:/config/mybatis-config.xml"));
			// TODO 多个怎么办
			bean.setMapperLocations(resolver.getResources("classpath*:com/yueny/kapo/maps/singleTable-**-Mapping.xml"));
			return bean.getObject();
		} catch (final Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(final SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
}
