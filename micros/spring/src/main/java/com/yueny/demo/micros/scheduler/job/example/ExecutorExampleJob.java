package com.yueny.demo.micros.scheduler.job.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.yueny.demo.common.example.service.IDataPrecipitationService;
import com.yueny.demo.micros.scheduler.BaseSuperScheduler;
import com.yueny.demo.micros.scheduler.job.example.runner.DemoMockBatchModifyStockRunner;
import com.yueny.rapid.lang.util.UuidUtil;
import com.yueny.rapid.lang.util.collect.CollectionUtil;
import com.yueny.rapid.lang.util.time.SystemClock;
import com.yueny.superclub.util.exec.async.factory.ExecutorServiceObjectFactory;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年4月18日 下午6:26:57
 *
 */
@Service
public class ExecutorExampleJob extends BaseSuperScheduler {
	@Autowired
	private IDataPrecipitationService dataPrecipitationService;

	private final ExecutorService executorService = ExecutorServiceObjectFactory
			.getExecutorServiceObject("executor-asyn-example").createExecutorService();

	@PostConstruct
	public void doSomething() {
		logger.info("ExecutorExampleJob doSomething...");
	}

	/**
	 *
	 */
	@Scheduled(cron = "0/5 * * * * ?")
	public void processData() {
		final long start = SystemClock.now();
		final String batchId = UuidUtil.getSimpleUuid();

		// 得到服务器中待批量处理数据的主键信息, limit 50
		final List<Long> ids = dataPrecipitationService.queryAllIds();

		// 数据分片, 分片项为 shardingTotalCount 或 taskExecutor.getCorePoolSize()
		final int shardingTotal = 10;
		final List<List<Long>> lists = CollectionUtil.<Long> split(ids, shardingTotal);
		logger.debug("批次{}分片项:{}/{}.", batchId, shardingTotal, lists.size());

		// 执行任务列表
		final List<Future<List<Long>>> tasks = new ArrayList<>();
		lists.stream().forEach(ls -> {
			final DemoMockBatchModifyStockRunner task = new DemoMockBatchModifyStockRunner(batchId, fetchData(ls),
					dataPrecipitationService);

			final Future<List<Long>> future = executorService.submit(task);
			tasks.add(future);
		});

		if (CollectionUtils.isEmpty(tasks)) {
			return;
		}

		logger.debug("等待所有任务完成:{}", tasks.size());
		final List<Long> updateResult = Lists.newArrayList();
		for (final Future<List<Long>> future : tasks) {
			try {
				updateResult.addAll(future.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}

		final long end = SystemClock.now();
		logger.info("Spring Update Batch id: {} 总耗时:{}秒, 更新数目:{}条.", batchId, (end - start) / 1000,
				updateResult.size());
	}

	private List<Long> fetchData(final List<Long> ids) {
		return ids;
	}

}
