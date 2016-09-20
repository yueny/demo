package com.yueny.demo;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月29日 上午1:24:41
 *
 */
public class TestApplicationContextInitializer implements
		ApplicationContextInitializer<AbstractApplicationContext> {

	@Override
	public void initialize(final AbstractApplicationContext applicationContext) {
		// final PropertyPlaceholderConfigurer propertyPlaceholderConfigurer =
		// new PropertyPlaceholderConfigurer();
		// propertyPlaceholderConfigurer.setLocation(new ClassPathResource(
		// "a.properties"));
		//
		// applicationContext
		// .addBeanFactoryPostProcessor(propertyPlaceholderConfigurer);

	}
}