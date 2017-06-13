package com.yueny.demo.job.scheduler.springjob;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.google.common.collect.Lists;
import com.yueny.demo.job.scheduler.base.BaseSuperScheduler;
import com.yueny.demo.job.scheduler.springjob.runner.DemoMockBatchModifyStockRunner;
import com.yueny.demo.job.service.IDataPrecipitationService;
import com.yueny.rapid.lang.util.UuidUtil;
import com.yueny.rapid.lang.util.collect.CollectionUtil;
import com.yueny.rapid.lang.util.time.SystemClock;

/**
 * 简单的模拟交易动作, 取全表数据进行随机状态变更<br>
 * task-spring.xml配置
 *
 * @author <a href="mailto:yueny09@126.com"> 袁洋 2014年12月9日 下午4:52:34
 *
 * @category tag
 */
public class DataBatchWithModifyForSpringJob extends BaseSuperScheduler {
	private static volatile boolean complete = false;
	@Autowired
	private IDataPrecipitationService dataPrecipitationService;

	// private final ThreadPoolExecutor taskExecutor = new ThreadPoolExecutor(2,
	// 4, 3, TimeUnit.SECONDS,
	// new ArrayBlockingQueue<Runnable>(3), new
	// ThreadPoolExecutor.CallerRunsPolicy());
	@Resource(name = "threadPoolTaskExecutor")
	private ThreadPoolTaskExecutor taskExecutor;

	/**
	 *
	 */
	public void processData() {
		complete = false;
		final long start = SystemClock.now();
		final String batchId = UuidUtil.getSimpleUuid();

		// 得到服务器中待批量处理数据的主键信息, limit 50
		final List<Long> ids = dataPrecipitationService.queryAllIds();
		// 数据分片, 分片项为 shardingTotalCount 或 taskExecutor.getCorePoolSize()
		final int shardingTotal = taskExecutor.getCorePoolSize();
		final List<List<Long>> lists = CollectionUtil.<Long> split(ids, shardingTotal);
		logger.debug("批次{}分片项:{}/{}.", batchId, shardingTotal, lists.size());

		// // 机器分片, 每片处理一个数据片
		// final List<List<List<Long>>> listm = CollectionUtil.<List<Long>>
		// split(lists, 1);

		// 执行任务列表
		final List<DemoMockBatchModifyStockRunner> tasks = new ArrayList<>();
		lists.stream().forEach(ls -> {
			tasks.add(new DemoMockBatchModifyStockRunner(batchId, fetchData(ls), dataPrecipitationService));
		});

		if (CollectionUtils.isEmpty(tasks)) {
			return;
		}

		// 等待所有任务完成后的结果
		final CompletionService<List<Long>> cs = new ExecutorCompletionService<>(taskExecutor);
		final List<Long> updateResult = Lists.newArrayList();

		/* method one */
		try {
			for (final Callable<List<Long>> task : tasks) {
				cs.submit(task);
			}

			logger.debug("等待所有任务完成:{}", tasks.size());
			for (int i = 0; i < tasks.size(); i++) {
				try {
					updateResult.addAll(cs.take().get());
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			}
			complete = true;
		} finally {
			if (!complete) {
				// 异常
			}
		}

		final long end = SystemClock.now();
		logger.info("Spring Update Batch id: {} 总耗时:{}秒, 更新数目:{}条.", batchId, (end - start) / 1000,
				updateResult.size());
	}

	private List<Long> fetchData(final List<Long> ids) {
		return ids;
	}

	private int sum(final List<Long> lists) {
		int sum = 0;
		for (final Long i : lists) {
			sum += i;
		}
		return sum;
	}

}