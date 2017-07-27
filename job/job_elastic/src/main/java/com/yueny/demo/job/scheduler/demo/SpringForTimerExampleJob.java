package com.yueny.demo.job.scheduler.demo;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.InitializingBean;

import com.yueny.demo.job.scheduler.base.BaseSuperScheduler;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年4月18日 下午6:26:57
 *
 */
// @Component
public class SpringForTimerExampleJob extends BaseSuperScheduler implements InitializingBean {
	/**
	 * Timer,内部实现使用的是一个单线程在运行
	 */
	private final Timer timer = new Timer();

	@Override
	public void afterPropertiesSet() throws Exception {
		// 8秒一次
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					System.out.println("SpringForTimerExampleJob:" + new Date());
				} catch (final Throwable t) { // 防御性容错
					logger.error("Unexpected error occur at sender monitor, cause: " + t.getMessage(), t);
				}
			}
		}, 1000L, 8 * 1000L);
	}

}
