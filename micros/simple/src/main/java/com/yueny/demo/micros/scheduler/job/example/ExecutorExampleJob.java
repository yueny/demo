package com.yueny.demo.micros.scheduler.job.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.MoreExecutors;
import com.yueny.demo.micros.scheduler.job.example.runner.DemoMockBatchModifyStockRunner;
import com.yueny.rapid.lang.util.UuidUtil;
import com.yueny.rapid.lang.util.collect.CollectionUtil;
import com.yueny.rapid.lang.util.time.SystemClock;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年4月18日 下午6:26:57
 *
 */
@Slf4j
public class ExecutorExampleJob {
	private static ScheduledExecutorService executor = MoreExecutors
			.listeningDecorator(MoreExecutors.getExitingScheduledExecutorService(new ScheduledThreadPoolExecutor(1)));
	// private static final ExecutorService executorService =
	// ExecutorServiceObjectFactory
	// .getExecutorServiceObject("executor-asyn-example").createExecutorService();
	private static ExecutorService executorService = MoreExecutors
			.listeningDecorator(MoreExecutors.getExitingExecutorService(new ThreadPoolExecutor(
					Runtime.getRuntime().availableProcessors() * 2, Runtime.getRuntime().availableProcessors() * 2, 5L,
					TimeUnit.MINUTES, new LinkedBlockingQueue<>())));

	public void init() {
		executor.scheduleWithFixedDelay(() -> {
			try {
				log.info("ExecutorExampleJob...");
				processData();
			} catch (final Exception e) {
				log.error("超时，下次继续.");
			}
		}, 0, /* N second */5 * 1000L, TimeUnit.MILLISECONDS);
	}

	/**
	 *
	 */
	private void processData() {
		final long start = SystemClock.now();
		final String batchId = UuidUtil.getNumberUuid();

		final Random rn = new Random();
		final List<Long> ids = Lists.newArrayList();
		for (int i = 0; i < 1000; i++) {
			ids.add(rn.nextLong());
		}

		final List<List<Long>> lists = CollectionUtil.<Long> split(ids, 100);

		// 执行任务列表
		final List<Future<List<Long>>> tasks = new ArrayList<>();
		final CompletionService<List<Long>> execComp = new ExecutorCompletionService<>(executorService);
		lists.stream().forEach(dataId -> {
			final DemoMockBatchModifyStockRunner task = new DemoMockBatchModifyStockRunner(batchId, dataId);

			final Future<List<Long>> future = execComp.submit(task);
			tasks.add(future);
		});

		if (CollectionUtils.isEmpty(tasks)) {
			return;
		}

		log.info("等待所有任务完成:{}", tasks.size());
		final List<Long> updateResult = Lists.newArrayList();
		for (int i = 0; i < tasks.size(); i++) {
			try {
				updateResult.addAll(execComp.take().get());
			} catch (final InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}

		final long end = SystemClock.now();
		log.info("Batch id: {} 总耗时:{}秒, 更新数目:{}条.", batchId, (end - start) / 1000, updateResult.size());
	}

}
