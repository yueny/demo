package com.yueny.demo.downlatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.yueny.demo.downlatch.async.PurchasedFailTransRecoverRunner;
import com.yueny.demo.downlatch.bo.RecoverResult;
import com.yueny.demo.downlatch.holder.TransResultHolder;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年3月22日 下午1:06:30
 *
 */
@Service
public class CountDownLatchDemoService {
	private static Logger logger = LoggerFactory.getLogger(CountDownLatchDemoService.class);
	/**
	 * Spring task
	 */
	@Autowired
	private AsyncTaskExecutor transRecoverExcutor;

	/**
	 *
	 */
	public void recover(final List<String> transIdList, final String message)
			throws InterruptedException, InstantiationException, IllegalAccessException {
		if (CollectionUtils.isEmpty(transIdList)) {
			System.out.println(message + "have no data need to recovered...");
			return;
		}

		// 分解任务
		final List<List<String>> parTransIdList = partition(transIdList, 5);

		// 创建倒计时门闩
		final CountDownLatch latch = new CountDownLatch(parTransIdList.size());
		logger.info("我初始化了 {} 个倒计时门闩", parTransIdList.size());

		final TransResultHolder resultHolder = new TransResultHolder();
		final long start = System.currentTimeMillis();

		// 处理分片的数据项
		int i = 0;
		for (final List<String> trans : parTransIdList) {
			if (i != 0 && i % 3 == 0) {
				try {
					TimeUnit.SECONDS.sleep(3);
				} catch (final InterruptedException e) {
					System.out.println("sleep Interrupted!");
				}
			}

			final PurchasedFailTransRecoverRunner task = new PurchasedFailTransRecoverRunner();
			task.init(trans, resultHolder, latch);
			transRecoverExcutor.execute(task);

			i++;
		}

		try {
			logger.info("倒计时门闩等待待中哦...");
			// 最长等待 6s，之后结束等待
			latch.await(6L, TimeUnit.SECONDS);
		} catch (final InterruptedException e) {
			// throw new RequestTimeoutException(e);
		}

		printResults(resultHolder, message, start);
	}

	private <T> List<List<T>> partition(final List<T> baseList, final Integer partitionSize) {
		Assert.notEmpty(baseList);
		Assert.isTrue(partitionSize != null && partitionSize > 0, "partionLimit is illegal.");
		final int totalCount = baseList.size();
		final int partitionCount = totalCount % partitionSize > 0 ? totalCount / partitionSize + 1
				: totalCount / partitionSize;
		final List<List<T>> partitionList = new ArrayList<List<T>>();
		for (int index = 1; index <= partitionCount; index++) {
			if (index < partitionCount) {
				final List<T> subList = baseList.subList((index - 1) * partitionSize, partitionSize * index);
				partitionList.add(subList);
			} else {
				final List<T> subList = baseList.subList((index - 1) * partitionSize, totalCount);
				partitionList.add(subList);
			}
		}
		return partitionList;
	}

	/**
	 * 打印补单处理结果s
	 *
	 * @param resultHolder
	 * @param processPhrase
	 */
	private void printResults(final TransResultHolder resultHolder, final String message, final long time) {
		logger.info("全部处理完了，开始输出了...");

		final List<String> succ = resultHolder.getSuccList();
		final List<RecoverResult> fail = resultHolder.getFailList();
		for (int i = 0; i < succ.size(); i++) {
			logger.info("success --> trans_id：{},{}", succ.get(i), message);
		}
		for (int i = 0; i < fail.size(); i++) {
			logger.info("failure --> trans_id：{},{}, reason：{}", fail.get(i).getTransId(), message,
					fail.get(i).getReason());
		}

		final long end = System.currentTimeMillis();
		logger.info("{}处理总数:{}, 成功:{}, 失败：{}, 耗时:{}毫秒。", message, resultHolder.getRecoverCount(), succ.size(),
				fail.size(), end - time);
	}

}
