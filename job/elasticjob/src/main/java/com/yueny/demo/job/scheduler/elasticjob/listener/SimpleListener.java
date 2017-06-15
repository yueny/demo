package com.yueny.demo.job.scheduler.elasticjob.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;

public class SimpleListener implements ElasticJobListener {
	// @Autowired
	// private IDistributedLock redisLock;

	@Override
	public void afterJobExecuted(final ShardingContexts shardingContexts) {
		System.out.println("SimpleListener afterJobExecuted:" + shardingContexts.getJobName());
	}

	@Override
	public void beforeJobExecuted(final ShardingContexts shardingContexts) {
		System.out.println("SimpleListener beforeJobExecuted:" + shardingContexts.getJobName());
	}
}
