package com.yueny.demo.job.scheduler.elasticjob.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.AbstractDistributeOnceElasticJobListener;

public class SimpleDistributeListener extends AbstractDistributeOnceElasticJobListener {

	private final long completedTimeoutMilliseconds;

	private final long startedTimeoutMilliseconds;

	public SimpleDistributeListener(final long startedTimeoutMilliseconds, final long completedTimeoutMilliseconds) {
		super(startedTimeoutMilliseconds, completedTimeoutMilliseconds);
		this.startedTimeoutMilliseconds = startedTimeoutMilliseconds;
		this.completedTimeoutMilliseconds = completedTimeoutMilliseconds;
	}

	@Override
	public void doAfterJobExecutedAtLastCompleted(final ShardingContexts shardingContexts) {
		System.out.println(
				"doAfterJobExecutedAtLastCompleted:" + startedTimeoutMilliseconds + "," + completedTimeoutMilliseconds);
	}

	@Override
	public void doBeforeJobExecutedAtLastStarted(final ShardingContexts shardingContexts) {
		System.out.println("doBeforeJobExecutedAtLastStarted:" + shardingContexts);
	}
}
