package com.yueny.demo.dynamic.scheduler.web.context;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * web.xml中listener配置
 * 
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月29日 上午1:13:28
 *
 */
public class BeanContextLoaderListener extends ContextLoaderListener {

	@Override
	public void contextInitialized(final ServletContextEvent event) {
		super.contextInitialized(event);
		final WebApplicationContext applicationContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(event.getServletContext());
		// BeanManager.initialize(applicationContext);

		initialContextParams(event.getServletContext());
	}

	private void initialContextParams(final ServletContext servletContext) {
		// .
	}

}