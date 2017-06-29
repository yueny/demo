package com.yueny.demo.job.scheduler.demo;

import org.springframework.scheduling.annotation.Scheduled;

import com.yueny.demo.job.scheduler.base.BaseSuperScheduler;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年4月18日 下午6:26:57
 *
 */
// @Component
public class ExampleJob extends BaseSuperScheduler {
	// @Value("${aaaa:0}")
	// private String aaaa;
	// @Value(value = "${memcached.serverlist}")
	// private String memcachedAddress;
	// @Value("${zookeeper.address}")
	// private String zookeeperAddress;

	/**
	 *
	 */
	@Scheduled(cron = "0/30 * * * * ?")
	public void processData() {
		System.out.println("ExampleJob");

		// logger.info("ExampleJob with memcachedAddress {} and {} ...",
		// memcachedAddress, zookeeperAddress);
		// System.out.println(aaaa);
	}

}
