package com.yueny.demo.task.scheduler.quartz;

import java.util.Date;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DemoSimplePropertyScheduler implements InitializingBean, DisposableBean {
	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("【DemoSimplePropertyScheduler】afterPropertiesSet!");
	}

	@Override
	public void destroy() throws Exception {
		log.info("【DemoSimplePropertyScheduler】destroy!");
	}

	/**
	 *
	 */
	public boolean lsogst() {
		log.info("【DemoSimplePropertyScheduler】lsogst:{} trigger...",
				this.getClass().getName().concat(new Date().toString()));
		return true;
	}

}