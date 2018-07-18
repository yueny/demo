package com.yueny.demo.dubbo.config.spring;

import java.lang.reflect.Method;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.support.AbstractApplicationContext;

import com.yueny.demo.dubbo.config.api.KapoConfig;

/**
 * LockBean
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年3月31日 下午6:14:56
 *
 */
@SuppressWarnings("rawtypes")
public class KapoBean extends KapoConfig
		implements InitializingBean, DisposableBean, ApplicationContextAware, ApplicationListener, BeanNameAware {
	/**
	 *
	 */
	private static final long serialVersionUID = -3987864786205207635L;

	protected static final Logger logger = LoggerFactory.getLogger(KapoBean.class);

	@SuppressWarnings("unused")
	private transient ApplicationContext applicationContext;
	@SuppressWarnings("unused")
	private transient String beanName;

	public KapoBean() {
		super();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		/* 针对<lock:lock id='xxx'>的配置， key is ID, value is LockBean instance */
		final Map<String, KapoConfig> lockConfigMap = applicationContext == null ? null
				: BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, KapoConfig.class, false, false);
		logger.info("{}", lockConfigMap);
	}

	@Override
	public void destroy() throws Exception {
		// .
	}

	@Override
	public void onApplicationEvent(final ApplicationEvent event) {
		if (ContextRefreshedEvent.class.getName().equals(event.getClass().getName())) {
			// .
		}
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;

		if (applicationContext != null) {
			try {
				final Method method = applicationContext.getClass().getMethod("addApplicationListener",
						new Class<?>[] { ApplicationListener.class }); // 兼容Spring2.0.1
				method.invoke(applicationContext, new Object[] { this });
			} catch (final Throwable t) {
				if (applicationContext instanceof AbstractApplicationContext) {
					try {
						final Method method = AbstractApplicationContext.class.getDeclaredMethod("addListener",
								new Class<?>[] { ApplicationListener.class }); // 兼容Spring2.0.1
						if (!method.isAccessible()) {
							method.setAccessible(true);
						}
						method.invoke(applicationContext, new Object[] { this });
					} catch (final Throwable t2) {
					}
				}
			}
		}
	}

	@Override
	public void setBeanName(final String name) {
		this.beanName = name;
	}

}
