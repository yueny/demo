package com.yueny.demo.job.scheduler.job.ej.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;

public class SimpleListener implements ElasticJobListener {

	@Override
	public void afterJobExecuted(final ShardingContexts shardingContexts) {
		System.out.println("afterJobExecuted:" + shardingContexts.getJobName());
	}

	@Override
	public void beforeJobExecuted(final ShardingContexts shardingContexts) {
		System.out.println("beforeJobExecuted:" + shardingContexts.getJobName());
	}
}
