package com.yueny.demo.job.scheduler.elasticjob.simple;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年6月13日 下午4:21:05
 *
 */
@Slf4j
public class DemoSimpleJob implements SimpleJob {
	@Override
	public void execute(final ShardingContext shardingContext) {
		log.info("Thread ID: {}, Sharding Context: {}.", Thread.currentThread().getId(), shardingContext);
	}

}
