package com.yueny.demo.dubbo.config.api;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.yueny.demo.dubbo.config.api.base.AbstractServiceConfig;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年4月1日 下午5:24:13
 *
 */
public class LockConfig extends AbstractServiceConfig implements InitializingBean, ApplicationContextAware {
	/**
	 *
	 */
	private static final long serialVersionUID = -2576136950726107617L;
	private transient ApplicationContext applicationContext;
	/**
	 * lock 键
	 */
	@Getter
	@Setter
	private String key;
	/**
	 * name
	 */
	@Getter
	@Setter
	private String name;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		/* 针对<lock:lock id='xxx'>的配置， key is ID, value is LockBean instance */
		final Map<String, LockConfig> lockConfigMap = applicationContext == null ? null
				: BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, LockConfig.class, false, false);
		logger.info("{}", lockConfigMap);
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
