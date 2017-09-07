package com.yueny.demo.micros.boot.spring.configure;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年11月16日 上午9:48:39
 *
 */
/*
 * 申明让spring
 * boot自动给程序进行必要的配置，这个配置等价于以默认属性使用 @Configuration @EnableAutoConfiguration @ComponentScan。
 *
 * @EnableAutoConfiguration：自动化配置。Spring应用上下文的自动化配置，尝试去猜测和配置你需要的bean。
 * 这些被自动化配置的类通常在classpath路径下，
 * 或者是你自己定义的bean。可以使用exclude来排除不想被自动化配置的类。被@AutoConfiguration注解的类所在的包有着特殊的意义，
 * 他们通常被认为是默认的包，并对其及下属的包进行扫描。
 *
 * @ComponentScan注解搜索beans，并结合@Autowired构造器注入。使得所有应用程序组件（
 *
 * @Component, @Service, @Repository, @Controller等）将被自动注册为Spring Beans。
 */
@SpringBootApplication
@ComponentScan(basePackages = { "com.yueny.kapo.core", // kapo database support
		"com.yueny.demo.common.example", // example database
		"com.yueny.demo.micros.boot.spring.configure" })
/*
 * 需要使用基于XML的配置，在@Configuration所在的类开始。 使用附加的@ImportResource注解加载XML配置文件
 */
@ImportResource(locations = { "classpath:/config/cfg-properties.xml", "classpath:/config/spring-*.xml" })
@EnableScheduling
@EnableTransactionManagement(proxyTargetClass = true) // 启用JPA事务管理
@Slf4j
/// *
// * @Import注解可以用来导入其他配置类
// */
// @Import
public class AgentBootApplication extends SpringBootServletInitializer implements DisposableBean {
	private static Class<AgentBootApplication> applicationClass = AgentBootApplication.class;

	public static void main(final String[] args) {
		try {
			SpringApplication.run(AgentBootApplication.class, args);
		} catch (final Exception e) {
			log.error("服务启动异常:", e);
		}
	}

	/*
	 * Application exit
	 *
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	@Override
	public void destroy() throws Exception {
		log.error("服务关闭了~~~");
	}

	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}

}
