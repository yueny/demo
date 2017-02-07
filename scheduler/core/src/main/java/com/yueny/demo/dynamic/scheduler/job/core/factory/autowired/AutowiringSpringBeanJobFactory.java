package com.yueny.demo.dynamic.scheduler.job.core.factory.autowired;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

/**
 * 使job类支持spring的自动注入<br>
 * AutowiringSpringBeanJobFactory类是为了可以在scheduler中使用spring注解，如果不使用注解，可以不适用该类，
 * 而直接使用 SpringBeanJobFactory
 *
 * @author yueny09
 *
 */
public class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {
	private transient AutowireCapableBeanFactory beanFactory;

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		beanFactory = applicationContext.getAutowireCapableBeanFactory();
	}

	@Override
	protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
		// 调用父类的方法
		final Object jobInstance = super.createJobInstance(bundle);

		// 进行注入,这属于Spring的技术
		beanFactory.autowireBean(jobInstance);
		// beanFactory.autowireBeanProperties(jobInstance,
		// AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, false);

		return jobInstance;
	}
}
