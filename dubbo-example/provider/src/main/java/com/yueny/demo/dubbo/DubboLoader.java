package com.yueny.demo.dubbo;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.yueny.demo.dubbo.api.GreetingService;
import com.yueny.demo.dubbo.api.IGreetingService;

import lombok.extern.slf4j.Slf4j;

/**
 * dubbo服务加载器
 *
 * Created by yueny on 2018/7/18 0018.
 */
@Service
@Slf4j
public class DubboLoader  implements BeanFactoryAware, BeanNameAware, InitializingBean {
	
	private List<ServiceConfig<?>> serviceConfigList = new ArrayList<>();
	
	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("【InitializingBean接口】调用InitializingBean.afterPropertiesSet()");
		
		// 创建服务提供者
		ServiceConfig<GreetingService> serviceConfig = new ServiceConfig<GreetingService>();
        serviceConfig.setApplication(new ApplicationConfig("demo-dubbo-provider"));

        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol("zookeeper");
        registryConfig.setAddress("47.93.158.14:2188");
        registryConfig.setTimeout(100000);
        serviceConfig.setRegistry(registryConfig);

        serviceConfig.setInterface(IGreetingService.class);
        serviceConfig.setRef(new GreetingService());

        serviceConfigList.add(serviceConfig);

        /*
         * dubbo export服务的时候主要做了以下几件事:
         *
         *  将服务export到本地（根据scope的配置）
         *  创建Invoker（启动本地NettyServer，监听指定端口，等待请求）
         *  注册provider的信息到registry，供consumer发现并订阅服务
         *  订阅registry中的configurator节点，可以动态更改部分provider的配置
         *
   		 */
   		for (ServiceConfig<?> sc : serviceConfigList) {
   			sc.export();
   		}
	}

	@Override
	public void setBeanName(String name) {
		log.info("【BeanNameAware接口】调用BeanNameAware.setBeanName()");
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		log.info("【BeanFactoryAware接口】调用BeanFactoryAware.setBeanFactory()");
	}
	
	@PostConstruct
	public void args() {
		log.info("【PostConstruct】调用@PostConstruct");
	}

}
