package com.yueny.demo.task.scheduler.spring;

import java.util.Date;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import lombok.extern.slf4j.Slf4j;

/**
 * demo任务
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月29日 下午5:14:13
 *
 */
@Slf4j
public class DemoSpringScheduler implements InitializingBean, DisposableBean {
	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("【DemoSpringScheduler】afterPropertiesSet!");
	}

	@Override
	public void destroy() throws Exception {
		log.info("【DemoSpringScheduler】destroy!");
	}

	/**
	 *
	 */
	public void lsogs() {
		log.info("【DemoSpringScheduler】lsogs轮询发送任务,每隔5秒触发一次。时间:{}", new Date());
	}

	/**
	 *
	 */
	public boolean lsogst() {
		log.info("【DemoSpringScheduler】lsogst轮询发送任务,每隔5秒触发一次。时间:{},结果:{}.", new Date(), true);
		return true;
	}

}
