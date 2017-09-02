package com.yueny.demo.micros.boot.spring.scheduler.job.example;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.MoreExecutors;
import com.yueny.rapid.lang.util.UuidUtil;
import com.yueny.rapid.lang.util.time.SystemClock;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年4月18日 下午6:26:57
 *
 */
@Slf4j
@Service
public class ExecutorExampleJob {
	private static ScheduledExecutorService executor = MoreExecutors
			.listeningDecorator(MoreExecutors.getExitingScheduledExecutorService(new ScheduledThreadPoolExecutor(1)));

	@PostConstruct
	public void doSomething() {
		executor.scheduleWithFixedDelay(() -> {
			try {
				init();
			} catch (final Exception e) {
				log.error("【ExecutorExampleJob任务】 超过超时，下次继续.");
			}
		}, 0, /* N second */2 * 500L, TimeUnit.MILLISECONDS);
	}

	public void init() {
		log.info("ExecutorExampleJob...");

		final String batchId = UuidUtil.getUUIDForNumber20();
		processData(batchId);
	}

	/**
	 *
	 */
	private void processData(final String batchId) {
		final long start = SystemClock.now();

		final Random rn = new Random();
		final List<Long> ids = Lists.newArrayList();
		for (int i = 0; i < 1000; i++) {
			ids.add(rn.nextLong());
		}

		final long end = SystemClock.now();
		// log.info("Batch id: {} 总耗时:{}秒, 更新数目:{}条.", batchId, (end - start) /
		// 1000, updateResult.size());
		log.info("Batch id: {} 总耗时:{}秒.", batchId, (end - start) / 1000);
	}

}
