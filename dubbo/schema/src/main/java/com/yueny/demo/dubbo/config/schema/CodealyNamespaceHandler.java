package com.yueny.demo.dubbo.config.schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import com.yueny.demo.dubbo.config.api.LockConfig;
import com.yueny.demo.dubbo.config.spring.KapoBean;

/**
 * CodealyNamespaceHandler
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年3月31日 下午6:39:15
 *
 */
public class CodealyNamespaceHandler extends NamespaceHandlerSupport {
	@Override
	public void init() {
		registerBeanDefinitionParser("lock", new CodealyBeanDefinitionParser(LockConfig.class));
		registerBeanDefinitionParser("kapo", new CodealyBeanDefinitionParser(KapoBean.class));
	}

}
