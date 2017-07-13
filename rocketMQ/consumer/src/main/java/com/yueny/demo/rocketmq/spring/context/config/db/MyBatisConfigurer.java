package com.yueny.demo.rocketmq.spring.context.config.db;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
public class MyBatisConfigurer {

	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactoryBean sqlSessionFactory(@Qualifier("dataSource") final DataSource dataSource) {
		final SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource);

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
			sqlSessionFactory.setConfigLocation(resolver.getResource("classpath:/config/mybatis-config.xml"));
			// TODO 多个怎么办
			sqlSessionFactory.setMapperLocations(
					resolver.getResources("classpath*:com/yueny/kapo/maps/singleTable-**-Mapping.xml"));
			// sqlSessionFactory.setTypeAliasesPackage("com.miz.asset.entity");
			return sqlSessionFactory;
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
