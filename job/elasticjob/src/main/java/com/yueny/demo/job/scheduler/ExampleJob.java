package com.yueny.demo.job.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.yueny.demo.job.scheduler.base.BaseSuperScheduler;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年4月18日 下午6:26:57
 *
 */
@Service
public class ExampleJob extends BaseSuperScheduler {
	/**
	 *
	 */
	@Scheduled(cron = "0/20 * * * * ?")
	public void processData() {
		logger.info("ExampleJob...");

	}

}
