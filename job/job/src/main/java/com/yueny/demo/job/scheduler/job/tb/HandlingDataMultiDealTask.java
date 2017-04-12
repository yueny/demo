package com.yueny.demo.job.scheduler.job.tb;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.collect.Lists;
import com.yueny.demo.job.bo.ModifyDemoBo;
import com.yueny.demo.job.scheduler.task.SuperTask;
import com.yueny.demo.job.service.IDataPrecipitationService;
import com.yueny.rapid.lang.json.JsonUtil;
import com.yueny.rapid.lang.util.UuidUtil;
import com.yueny.rapid.lang.util.time.SystemClock;
import com.yueny.superclub.util.crypt.util.TripleDesEncryptUtil;

/**
 * 处理中交易的状态重置
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年11月16日 下午1:39:13
 *
 */
public class HandlingDataMultiDealTask extends SuperTask implements Callable<List<Long>>, Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -7975387809977577316L;
	private final IDataPrecipitationService dataPrecipitationService;
	/**
	 * 任务号
	 */
	private final String taskId;
	/**
	 * 保存任务所需要的数据
	 */
	private final List<Long> ts;

	public HandlingDataMultiDealTask(final List<Long> ts, final IDataPrecipitationService dataPrecip) {
		this.ts = ts;
		this.dataPrecipitationService = dataPrecip;
		taskId = UuidUtil.getSimpleUuid();
	}

	public HandlingDataMultiDealTask(final Long[] ts, final IDataPrecipitationService dataPrecip) {
		this.ts = Arrays.asList(ts);
		this.dataPrecipitationService = dataPrecip;
		taskId = UuidUtil.getSimpleUuid();
	}

	@Override
	public List<Long> call() throws Exception {
		if (CollectionUtils.isEmpty(ts)) {
			return Collections.emptyList();
		}

		final List<Long> updateIds = Lists.newArrayList();
		final long start = SystemClock.now();
		for (final Long id : ts) {
			final ModifyDemoBo demoBo = dataPrecipitationService.findById(id);

			final String json = JsonUtil.toJson(demoBo);
			TripleDesEncryptUtil.tripleDesEncrypt(json);

			try {
				TimeUnit.MILLISECONDS.sleep(20);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}

			if (id % 10 == 0) {
				// 模拟结果可疑的数据
				continue;
			}

			demoBo.setType("Y");
			dataPrecipitationService.update(demoBo);

			updateIds.add(id);
		}

		final long end = SystemClock.now();
		logger.debug("任务号:{} 耗时:{}秒, 处理数据列表:{}.", taskId, (end - start) / 1000, updateIds);

		return updateIds;
	}

}
