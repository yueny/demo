/**
 *
 */
package com.yueny.demo.micros.boot.spring.configure.tester;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = { "com.yueny.demo.micros.boot.spring.configure" })
@ImportResource(locations = { "classpath:/config/cfg-properties.xml", "classpath:/config/spring-db.xml" })
@EnableScheduling
@EnableTransactionManagement // 启用JPA事务管理
@EnableAsync
@EnableJpaRepositories(basePackages = "com.yueny.demo.micros.boot.spring.configure.repository") // 启用JPA资源库并指定接口资源库位置
@EntityScan(basePackages = "com.yueny.demo.micros.boot.spring.configure.entry") // 定义实体位置
public class TesterForServiceContextConfiguration {
	// .
}
