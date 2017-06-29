/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package com.yueny.demo.job.scheduler.elasticjob.dataflow;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.yueny.demo.common.example.bo.ModifyDemoBo;
import com.yueny.demo.common.example.service.IDataPrecipitationService;
import com.yueny.demo.job.scheduler.base.IScheduler;
import com.yueny.rapid.lang.util.enums.YesNoType;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据流分布式作业<br>
 * Dataflow类型用于处理数据流，需实现DataflowJob接口。该接口提供2个方法可供覆盖，分别用于抓取(fetchData)和处理(
 * processData)数据。<br>
 * 代替<br>
 *
 * <pre>
 * <job:dataflow id="${dataflow.id}" class="${dataflow.class}" registry-center-ref="regCenter" sharding-total-count="${dataflow.shardingTotalCount}" cron="${dataflow.cron}" sharding-item-parameters="${dataflow.shardingItemParameters}" monitor-execution="${dataflow.monitorExecution}" failover="${dataflow.failover}" max-time-diff-seconds="${dataflow.maxTimeDiffSeconds}" streaming-process="${dataflow.streamingProcess}" description="${dataflow.description}" disabled="${dataflow.disabled}" overwrite="${dataflow.overwrite}" />
 * </pre>
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年11月16日 下午9:27:12
 *
 */
@Service
@Slf4j
public class SpringDataflowJob implements DataflowJob<Long>, IScheduler {
	private static Random rn = new Random();

	@Autowired
	private IDataPrecipitationService dataPrecipitationService;

	@Override
	public List<Long> fetchData(final ShardingContext shardingContext) {
		// switch (context.getShardingItem()) {
		// case 0:
		// List<Foo> data = // get data from database by sharding item 0
		// return data;
		// case 1:
		// List<Foo> data = // get data from database by sharding item 1
		// return data;
		// case 2:
		// List<Foo> data = // get data from database by sharding item 2
		// return data;
		// // case n: ...
		// }

		try {
			TimeUnit.SECONDS.sleep(10L);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		final List<Long> ids = dataPrecipitationService.quertIdsBySharding(shardingContext.getShardingTotalCount(),
				shardingContext.getShardingItem(), 10, YesNoType.N);
		log.info("获取TYPE is 'N' 的信息:{}， Thread ID: {}, Sharding Context: {}.", ids, Thread.currentThread().getId(),
				shardingContext);

		return ids;
	}

	@Override
	public void processData(final ShardingContext shardingContext, final List<Long> data) {
		// 建议processData处理数据后，更新其状态，避免fetchData再次抓取到，从而使得作业永远不会停止。
		for (final Long id : data) {
			final ModifyDemoBo each = dataPrecipitationService.findById(id);

			// id为5的整数倍的进行操作
			if (each.getId() % (rn.nextInt(5) + 1) == 0) {
				YesNoType type = YesNoType.N;
				if (rn.nextBoolean()) {
					type = YesNoType.Y;
					dataPrecipitationService.setInactive(each.getId(), type);
				}
			}
		}
	}

}
