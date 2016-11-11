package com.yueny.demo.metrics.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.yueny.demo.metrics.service.IDemoService;

/**
 *
 * @author <a href="mailto:yueny09@126.com"> 袁洋 2014年12月9日 下午4:52:34
 *
 * @category tag
 */
@Service
public class DemoScheduler extends SuperScheduler {
	@Autowired
	private IDemoService demoService;

	/**
	 * autoBars
	 */
	@Scheduled(cron = "0/2 * * * * ?")
	public void autoBars() {
		System.out.println(demoService.bar());
	}

	/**
	 * autoLogs
	 */
	@Scheduled(cron = "0/10 * * * * ?")
	public void autoLogs() {
		System.out.println("autoLogs...");
	}

}