package com.yueny.demo.downlatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

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
		if (!CollectionUtils.isEmpty(transIdList)) {
			// 分解任务
			final List<List<String>> parTransIdList = partition(transIdList, 10);

			// 创建倒计时门闩
			final CountDownLatch countDownLatch = new CountDownLatch(parTransIdList.size());
			final TransResultHolder resultHolder = new TransResultHolder();
			final long start = System.currentTimeMillis();
			for (int i = 0; i < parTransIdList.size(); i++) {
				if (i != 0 && i % 3 == 0) {
					Thread.sleep(3000L);
				}
				final PurchasedFailTransRecoverRunner task = new PurchasedFailTransRecoverRunner();
				task.init(parTransIdList.get(i), resultHolder, countDownLatch);
				transRecoverExcutor.execute(task);
			}
			countDownLatch.await();
			final long end = System.currentTimeMillis();
			printResults(resultHolder, message, end - start);
		} else {
			System.out.println(message + "have no data need to recovered...");
		}
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
		final List<String> succ = resultHolder.getSuccList();
		final List<RecoverResult> fail = resultHolder.getFailList();
		for (int i = 0; i < succ.size(); i++) {
			logger.info("trans_id：{},{}success", succ.get(i), message);
		}
		for (int i = 0; i < fail.size(); i++) {
			logger.error("trans_id：{},{}failure, reason：{}", fail.get(i).getTransId(), message,
					fail.get(i).getReason());
		}
		logger.info("{}处理总数:{}, 成功:{}, 失败：{}, 耗时:{}", message, resultHolder.getRecoverCount(), succ.size(), fail.size(),
				time);
	}

}
