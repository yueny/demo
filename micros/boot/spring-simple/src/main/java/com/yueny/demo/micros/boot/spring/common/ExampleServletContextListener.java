package com.yueny.demo.micros.boot.spring.common;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.stereotype.Component;

@Component
public class ExampleServletContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(final ServletContextEvent sce) {
		System.out.println("*** contextDestroyed");
	}

	@Override
	public void contextInitialized(final ServletContextEvent sce) {
		System.out.println("*** contextInitialized");
	}

}
