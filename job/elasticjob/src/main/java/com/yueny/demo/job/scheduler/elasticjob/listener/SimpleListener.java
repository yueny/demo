package com.yueny.demo.job.scheduler.elasticjob.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleListener implements ElasticJobListener {
	// @Autowired
	// private IDistributedLock redisLock;

	@Override
	public void afterJobExecuted(final ShardingContexts shardingContexts) {
		log.info("SimpleListener afterJobExecuted:{}.", shardingContexts.getJobName());
	}

	@Override
	public void beforeJobExecuted(final ShardingContexts shardingContexts) {
		log.info("SimpleListener beforeJobExecuted:{}.", shardingContexts);
	}

}
