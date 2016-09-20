package com.yueny.demo.frequence.sla.limit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.beans.Mergeable;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月15日 下午2:52:20
 *
 */
@Deprecated
public abstract class RegisterHandlerInterceptor
		implements HandlerInterceptor, BeanFactoryAware, BeanPostProcessor, Mergeable {
	@Override
	public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response,
			final Object handler, final Exception ex) throws Exception {

	}

	@Override
	public boolean isMergeEnabled() {
		return true;
	}

	@Override
	public Object merge(final Object parent) {
		if (parent instanceof Object[]) {
			final Object[] interceptors = (Object[]) parent;
			final Object[] args = new Object[interceptors.length + 1];
			System.arraycopy(interceptors, 0, args, 1, interceptors.length);
			args[0] = this;
			return args;
		}

		return new Object[] { this, parent };
	}

	@Override
	public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
			final ModelAndView modelAndView) throws Exception {

	}

	@Override
	public Object postProcessAfterInitialization(final Object bean, final String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(final Object bean, final String beanName) throws BeansException {
		return bean;
	}

	@Override
	public void setBeanFactory(final BeanFactory beanFactory) throws BeansException {
		final ConfigurableListableBeanFactory factory = ((ConfigurableListableBeanFactory) beanFactory);
		for (final String beanName : factory.getBeanDefinitionNames()) {
			final BeanDefinition beanDefinition = factory.getBeanDefinition(beanName);
			final boolean isHandlerMapping = isHandlerMapping(beanDefinition);
			if (isHandlerMapping) {

				System.out.println("setBeanFactory postProcessBeanFactory BeanClassName:"
						+ beanDefinition.getBeanClassName() + " class:" + this.getClass().getName());
				final MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();
				propertyValues.addPropertyValue("interceptors", this);
			}
		}
	}

	protected boolean isHandlerMapping(final BeanDefinition beanDefinition) {
		final String beanClassName = beanDefinition.getBeanClassName();
		if (beanClassName == null) {
			// throw new NullPointerException("beanDefinition:" +
			// beanDefinition);
			return false;
		}
		Class<?> clazz;
		try {
			clazz = Class.forName(beanClassName);
		} catch (final NoClassDefFoundError e) {
			e.printStackTrace();
			return false;
		} catch (final ClassNotFoundException e) {
			return false;
		}
		if (RequestMappingHandlerMapping.class.isAssignableFrom(clazz)) {
			return true;
		}
		return false;
	}

}
