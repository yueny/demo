package com.yueny.demo.job.scheduler.elasticjob.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.AbstractDistributeOnceElasticJobListener;

import lombok.extern.slf4j.Slf4j;

/**
 * 分布式场景中仅单一节点执行的监听<br>
 * 若作业处理数据库数据，处理完成后只需一个节点完成数据清理任务即可。此类型任务处理复杂，需同步分布式环境下作业的状态同步，
 * 提供了超时设置来避免作业不同步导致的死锁，请谨慎使用。
 * 
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年6月23日 下午2:53:58
 *
 */
@Slf4j
public class DistributeOnceJobListener extends AbstractDistributeOnceElasticJobListener {

	private final long completedTimeoutMilliseconds;

	private final long startedTimeoutMilliseconds;

	public DistributeOnceJobListener(final long startedTimeoutMilliseconds, final long completedTimeoutMilliseconds) {
		super(startedTimeoutMilliseconds, completedTimeoutMilliseconds);
		this.startedTimeoutMilliseconds = startedTimeoutMilliseconds;
		this.completedTimeoutMilliseconds = completedTimeoutMilliseconds;
	}

	@Override
	public void doAfterJobExecutedAtLastCompleted(final ShardingContexts shardingContexts) {
		log.info("SimpleDistributeListener doAfterJobExecutedAtLastCompleted:{},{}.", startedTimeoutMilliseconds,
				completedTimeoutMilliseconds);
	}

	@Override
	public void doBeforeJobExecutedAtLastStarted(final ShardingContexts shardingContexts) {
		log.info("SimpleDistributeListener doBeforeJobExecutedAtLastStarted:{}.", shardingContexts);
	}

}
