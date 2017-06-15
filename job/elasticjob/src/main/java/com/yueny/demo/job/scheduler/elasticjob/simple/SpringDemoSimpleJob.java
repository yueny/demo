package com.yueny.demo.job.scheduler.elasticjob.simple;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.yueny.demo.job.service.IDataPrecipitationService;

import lombok.extern.slf4j.Slf4j;

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
@Component
@Slf4j
public class SpringDemoSimpleJob implements SimpleJob {
	@Autowired
	private IDataPrecipitationService dataPrecipitationService;

	@Override
	public void execute(final ShardingContext shardingContext) {
		// System.out.println(String.format("------Thread ID: %s, Date: %s,
		// Sharding Context: %s, Action: %s",
		// Thread.currentThread().getId(), new Date(), shardingContext, "simple
		// job"));

		// List<String> result =
		// Splitter.on("-").trimResults().splitToList(input);
		final List<Long> ids = dataPrecipitationService.quertIdsBySharding(shardingContext.getShardingTotalCount(),
				shardingContext.getShardingItem(), 10);

		log.info("Thread ID: {}, Sharding Context: {}.", Thread.currentThread().getId(), shardingContext);
		System.out.println(
				String.format("Thread ID: %s, Sharding Context: %s.", Thread.currentThread().getId(), shardingContext));
	}

}
