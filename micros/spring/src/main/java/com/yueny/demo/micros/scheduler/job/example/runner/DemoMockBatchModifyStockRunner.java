package com.yueny.demo.micros.scheduler.job.example.runner;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.collect.Lists;
import com.yueny.demo.micros.example.bo.ModifyDemoBo;
import com.yueny.demo.micros.example.service.IDataPrecipitationService;
import com.yueny.demo.micros.scheduler.BaseSuperScheduler;
import com.yueny.rapid.lang.json.JsonUtil;
import com.yueny.rapid.lang.util.UuidUtil;
import com.yueny.rapid.lang.util.time.SystemClock;
import com.yueny.superclub.util.crypt.util.TripleDesEncryptUtil;

/**
 * 进行数据库更新操作的任务
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年11月16日 下午1:39:13
 *
 */
public class DemoMockBatchModifyStockRunner extends BaseSuperScheduler implements Callable<List<Long>>, Serializable {
	private static Random rn = new Random();
	/**
	 *
	 */
	private static final long serialVersionUID = -7975387809977577316L;
	/**
	 * 批次号
	 */
	private final String batchId;
	private final IDataPrecipitationService dataPrecipitationService;
	/**
	 * 保存任务所需要的数据
	 */
	private final List<Long> taskData;
	/**
	 * 任务号
	 */
	private final String taskId;

	public DemoMockBatchModifyStockRunner(final String batchId, final List<Long> ids,
			final IDataPrecipitationService dataPrecip) {
		this.batchId = batchId;
		this.taskData = ids;
		this.dataPrecipitationService = dataPrecip;
		taskId = UuidUtil.getSimpleUuid();
	}

	@Override
	public List<Long> call() throws Exception {
		if (CollectionUtils.isEmpty(taskData)) {
			return Collections.emptyList();
		}

		final List<Long> updateIds = Lists.newArrayList();
		final long start = SystemClock.now();
		for (final Long id : taskData) {
			final ModifyDemoBo demoBo = dataPrecipitationService.findById(id);

			final String json = JsonUtil.toJson(demoBo);
			TripleDesEncryptUtil.tripleDesEncrypt(json);

			try {
				TimeUnit.MILLISECONDS.sleep(200);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}

			// id为1-5的随机整数倍的进行操作
			if (demoBo.getId() % (rn.nextInt(5) + 1) == 0) {
				if (id % 10 == 0) {
					// 模拟结果可疑的数据,此时不产生随机状态
					continue;
				}

				if (rn.nextBoolean()) {
					demoBo.setType("Y");
				} else {
					demoBo.setType("N");
					updateIds.add(id);
				}
				dataPrecipitationService.update(demoBo);
			}
		}

		final long end = SystemClock.now();
		logger.debug("批次:{}子进程:{} 耗时:{}秒, 返回:{}, 总数据:{}条.", batchId, taskId, (end - start) / 1000, updateIds,
				taskData.size());

		return updateIds;
	}

}
