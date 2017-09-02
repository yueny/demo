package com.yueny.demo.micros.cloud.scheduler.runner;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.collect.Lists;
import com.yueny.rapid.lang.util.UuidUtil;
import com.yueny.rapid.lang.util.time.SystemClock;

import lombok.extern.slf4j.Slf4j;

/**
 * 进行数据库更新操作的任务
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年11月16日 下午1:39:13
 *
 */
@Slf4j
public class DemoMockBatchModifyStockRunner implements Callable<List<Long>>, Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -7975387809977577316L;
	/**
	 * 批次号
	 */
	private final String batchId;
	/**
	 * 保存任务所需要的数据
	 */
	private final List<Long> taskData;
	/**
	 * 任务号
	 */
	private final String taskId;

	public DemoMockBatchModifyStockRunner(final String batchId, final List<Long> ids) {
		this.batchId = batchId;
		this.taskData = ids;
		taskId = UuidUtil.getSimpleUuid();
	}

	@Override
	public List<Long> call() throws Exception {
		if (CollectionUtils.isEmpty(taskData)) {
			return Collections.emptyList();
		}

		final List<Long> updateIds = Lists.newArrayList(taskData);
		final long start = SystemClock.now();
		// for (final Long id : taskData) {
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		// }

		final long end = SystemClock.now();
		log.debug("批次:{}子进程:{} 耗时:{}秒, 返回:{}, 总数据:{}条.", batchId, taskId, (end - start) / 1000, updateIds,
				taskData.size());

		return updateIds;
	}

}
