package com.yueny.demo.job.scheduler.elasticjob.simple.spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.yueny.demo.common.example.service.IDataPrecipitationService;
import com.yueny.demo.job.scheduler.elasticjob.simple.AbstractSimpleJob;

/**
 * 实现<br>
 * <job:simple id="springDemoSimpleJob" class=
 * "com.yueny.demo.job.scheduler.elasticjob.simple.SpringDemoSimpleJob"
 * registry-center-ref="regCenter" sharding-total-count=
 * "${simple.demo.shardingTotalCount}" cron="${simple.demo.cron}"
 * sharding-item-parameters="${simple.demo.shardingItemParameters}"
 * monitor-execution="${simple.demo.monitorExecution}" monitor-port=
 * "${simple.demo.monitorPort}" failover="${simple.demo.failover}" description=
 * "${simple.demo.description}" disabled="${simple.demo.disabled}" overwrite=
 * "${simple.demo.overwrite}">
 *
 * <job:listener class=
 * "com.yueny.demo.job.scheduler.elasticjob.listener.SimpleListener"/>
 * </job:simple>
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年6月13日 下午4:21:05
 *
 */
@Service
public class SpringEjDemoSimpleJob extends AbstractSimpleJob {
	@Autowired
	private IDataPrecipitationService dataPrecipitationService;

	@Override
	public void executor(final ShardingContext shardingContext) {
		// switch (shardingContext.getShardingItem()) {
		// case 0:
		// // do something by sharding item 0
		// break;
		// case 1:
		// // do something by sharding item 1
		// break;
		// case 2:
		// // do something by sharding item 2
		// break;
		// // case n: ...
		// }

		// List<String> result =
		// Splitter.on("-").trimResults().splitToList(input);

		// shardingContext.getShardingTotalCount() 该任务总的分片项
		// shardingContext.getShardingItem() 当前分片项 0-N
		// shardingContext.getShardingParameter() 当前分片项的分片参数

		final List<Long> ids = dataPrecipitationService.quertIdsBySharding(shardingContext.getShardingTotalCount(),
				shardingContext.getShardingItem(), 10);

		System.out.println("SpringEjDemoSimpleJob...");
		logger.info("获取信息:{}， Thread ID: {}, Sharding Context: {}.", ids, Thread.currentThread().getId(),
				shardingContext);
	}

}
