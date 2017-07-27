package com.yueny.demo.job.scheduler.elasticjob.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;

import lombok.extern.slf4j.Slf4j;

/**
 * 每台作业节点均执行的监听<br>
 * 若作业处理作业服务器的文件，处理完成后删除文件，可考虑使用每个节点均执行清理任务。此类型任务实现简单，且无需考虑全局分布式任务是否完成，
 * 请尽量使用此类型监听器。
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年6月23日 下午2:51:43
 *
 */
@Slf4j
public class SimpleJobListener implements ElasticJobListener {
	@Override
	public void afterJobExecuted(final ShardingContexts shardingContexts) {
		log.info("SimpleListener afterJobExecuted:{}.", shardingContexts.getJobName());
	}

	@Override
	public void beforeJobExecuted(final ShardingContexts shardingContexts) {
		log.info("SimpleListener beforeJobExecuted:{}.", shardingContexts);
	}

}
