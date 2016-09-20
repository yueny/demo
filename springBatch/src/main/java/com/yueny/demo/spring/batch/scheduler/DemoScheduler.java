package com.yueny.demo.spring.batch.scheduler;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * demo任务
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月29日 下午5:14:13
 *
 */
@Service
@Slf4j
public class DemoScheduler implements InitializingBean, DisposableBean {
	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("【DemoScheduler】afterPropertiesSet!");
	}

	@Override
	public void destroy() throws Exception {
		log.info("【DemoScheduler】destroy!");
	}

	/**
	 *
	 */
	@Scheduled(cron = "0/5 * * * * ?")
	public void lsogs() {
		log.info("【DemoScheduler】邮件轮询发送任务,每隔5秒触发一次。时间:{}", new Date());
	}

}
