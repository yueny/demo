package com.yueny.demo.micros.boot.spring.configure.core.context.config.wrapper;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ExampleServletContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(final ServletContextEvent sce) {
		log.info("*** contextDestroyed");
	}

	@Override
	public void contextInitialized(final ServletContextEvent sce) {
		log.info("*** contextInitialized");
	}

}
