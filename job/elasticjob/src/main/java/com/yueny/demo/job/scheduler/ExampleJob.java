package com.yueny.demo.job.scheduler;

import org.springframework.beans.factory.annotation.Value;
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
// @Configuration
public class ExampleJob extends BaseSuperScheduler {
	@Value("${aaaa:0}")
	private String aaaa;
	@Value(value = "${memcached.serverlist}")
	private String memcachedAddress;
	@Value("${zookeeper.address}")
	private String zookeeperAddress;

	/**
	 *
	 */
	@Scheduled(cron = "0/3 * * * * ?")
	public void processData() {
		logger.info("ExampleJob with memcachedAddress {} and {} ...", memcachedAddress, zookeeperAddress);
		System.out.println(aaaa);
	}

}
