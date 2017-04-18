package com.yueny.demo.future;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.yueny.demo.common.annotion.ListUtil;
import com.yueny.demo.future.async.PreloaderFuture;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年3月22日 下午1:06:30
 *
 */
@Service
public class PreloaderDemoService {
	private static Logger logger = LoggerFactory.getLogger(PreloaderDemoService.class);

	private final ExecutorService executor = Executors.newFixedThreadPool(1);

	/**
	 *
	 */
	public void recover(final List<String> transIdList, final String message)
			throws InterruptedException, InstantiationException, IllegalAccessException {
		if (CollectionUtils.isEmpty(transIdList)) {
			System.out.println(message + "have no data need to recovered...");
			return;
		}

		final long start = System.currentTimeMillis();

		// 分解任务
		final List<List<String>> parTransIdList = ListUtil.partition(transIdList, 5);
		final CountDownLatch latch = new CountDownLatch(parTransIdList.size());

		final List<Future<Long>> futures = Lists.newArrayList();
		for (final List<String> trans : parTransIdList) {
			final Future<Long> future = executor.submit(new PreloaderFuture(trans, latch));
			futures.add(future);
		}

		try {
			latch.await(6L, TimeUnit.SECONDS);
		} catch (final InterruptedException e) {
			// ..
		}

		Long count = 0L;
		for (final Future<Long> future : futures) {
			try {
				final Long data = future.get();
				count += data;
			} catch (final InterruptedException e) {
				// 重新设置线程的中断状态
				// .
			} catch (final Exception e) {
				// TODO: handle exception
			}
		}

		final long end = System.currentTimeMillis();
		logger.info("总共处理数据：{}，耗时:{}毫秒。", count, end - start);
	}

}
