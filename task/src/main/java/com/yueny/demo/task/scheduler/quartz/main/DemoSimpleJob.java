package com.yueny.demo.task.scheduler.quartz.main;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月19日 下午1:47:14
 *
 */
@Slf4j
public class DemoSimpleJob implements InitializingBean, DisposableBean, Job {
	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("【DemoSimpleJob】afterPropertiesSet!");
	}

	@Override
	public void destroy() throws Exception {
		log.info("【DemoSimpleJob】destroy!");
	}

	@Override
	public void execute(final JobExecutionContext context) throws JobExecutionException {
		lsogst();
	}

	/**
	 *
	 */
	private boolean lsogst() {
		// sleep 2 seconds to simulate the job execution
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}

		log.info("【DemoSimpleJob】lsogst:{} trigger...", this.getClass().getName().concat(new Date().toString()));
		return true;
	}

}
