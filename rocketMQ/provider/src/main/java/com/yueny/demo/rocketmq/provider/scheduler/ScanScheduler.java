package com.yueny.demo.rocketmq.provider.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2015年12月10日 下午5:47:46
 *
 */
public class ScanScheduler {
	/** 日志 */
	private static final Logger logger = LoggerFactory.getLogger(ScanScheduler.class);

	/**
	 *
	 */
	@Scheduled(cron = "0/10 * * * * ?")
	public void scan() {
		logger.info("Scan scheduler ...");
	}

}
