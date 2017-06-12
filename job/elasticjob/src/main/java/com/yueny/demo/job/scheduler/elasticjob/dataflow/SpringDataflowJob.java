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

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.yueny.demo.job.bo.ModifyDemoBo;
import com.yueny.demo.job.service.IDataPrecipitationService;
import com.yueny.rapid.lang.util.enums.YesNoType;

/**
 * 数据流分布式作业
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年11月16日 下午9:27:12
 *
 */
// @Component
public class SpringDataflowJob implements DataflowJob<Long> {
	private static Random rn = new Random();

	@Resource
	private IDataPrecipitationService dataPrecipitationService;

	@Override
	public List<Long> fetchData(final ShardingContext shardingContext) {
		System.out.println(String.format("------Thread ID: %s, Date: %s, Sharding Context: %s, Action: %s",
				Thread.currentThread().getId(), new Date(), shardingContext, "dataflow job fetch data"));

		// return
		// dataPrecipitationService.quertIdsBySharding(shardingContext.getShardingItem());
		return null;
	}

	@Override
	public void processData(final ShardingContext shardingContext, final List<Long> data) {
		// 建议processData处理数据后，更新其状态，避免fetchData再次抓取到，从而使得作业永远不会停止。
		System.out.println(String.format("------Thread ID: %s, Date: %s, Sharding Context: %s, Action: %s, Data: %s",
				Thread.currentThread().getId(), new Date(), shardingContext, "dataflow job process data", data));

		for (final Long id : data) {
			final ModifyDemoBo each = dataPrecipitationService.findById(id);

			// id为5的整数倍的进行操作
			if (each.getId() % (rn.nextInt(5) + 1) == 0) {
				YesNoType type = YesNoType.N;
				if (rn.nextBoolean()) {
					type = YesNoType.Y;
				}
				dataPrecipitationService.setInactive(each.getId(), type);
			}
		}
	}

}
