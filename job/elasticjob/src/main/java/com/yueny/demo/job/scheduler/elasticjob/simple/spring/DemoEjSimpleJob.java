package com.yueny.demo.job.scheduler.elasticjob.simple.spring;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.yueny.demo.job.scheduler.elasticjob.simple.AbstractSimpleJob;

/**
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年6月13日 下午4:21:05
 *
 */
// @Service
public class DemoEjSimpleJob extends AbstractSimpleJob {
	@Override
	public void executor(final ShardingContext shardingContext) {
		logger.info("Thread ID: {}, Sharding Context: {}.", Thread.currentThread().getId(), shardingContext);
	}

}
