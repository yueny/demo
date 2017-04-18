package com.yueny.demo.job.scheduler.job.tb;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;

import javax.annotation.Resource;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.taobao.pamirs.schedule.IScheduleTaskDealMulti;
import com.taobao.pamirs.schedule.TaskItemDefine;
import com.yueny.demo.job.scheduler.BaseSuperScheduler;
import com.yueny.demo.job.service.IDataPrecipitationService;
import com.yueny.rapid.lang.util.time.SystemClock;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年11月21日 下午10:01:26
 */
@Service
public class HandlingDataMultiDealJob extends BaseSuperScheduler implements IScheduleTaskDealMulti<Long> {
	@Autowired
	private IDataPrecipitationService dataPrecipitationService;
	@Resource(name = "threadPoolTaskExecutor")
	private ThreadPoolTaskExecutor taskExecutor;

	@Override
	public boolean execute(final Long[] ids, final String ownSign) throws Exception {
		logger.debug("ownSign:{}，ids:{}", ownSign, ids);

		final long start = SystemClock.now();

		// 执行任务列表
		final List<HandlingDataMultiDealTask> tasks = Lists.newArrayList();
		tasks.add(new HandlingDataMultiDealTask(ids, dataPrecipitationService));

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
		} finally {
		}

		final long end = SystemClock.now();
		logger.info("{} 下 执行ids:{}, 汇总执行结果:{}, 总耗时:{}秒.", ownSign, ids, updateResult, (end - start) / 1000);
		return true;
	}

	@Override
	public Comparator<Long> getComparator() {
		return null;
	}

	/**
	 * 根据条件，查询当前调度服务器可处理的任务
	 *
	 * @param taskParameter
	 *            任务的自定义参数
	 * @param ownSign
	 *            当前环境名称
	 * @param taskQueueNum
	 *            当前任务项的任务队列数量
	 * @param taskItemList
	 *            集合中TaskItemDefine的id值对应任务项值，多线程处理时，根据任务项协调数据一致性和完整性
	 * @param eachFetchDataNum
	 *            每次获取数据的数量.对应控制台每次获取数量，由于子计时单元开始后，会不断的去取数据进行处理，直到取不到数据子计时才停止，
	 *            等待下一个子计时开始。可以限制每次取数，防止一次性数据记录过大，内存不足
	 * @throws Exception
	 */
	@Override
	public List<Long> selectTasks(final String taskParameter, final String ownSign, final int taskQueueNum,
			final List<TaskItemDefine> taskItemList, final int eachFetchDataNum) throws Exception {
		final List<Integer> taskItemValues = Lists.newArrayList();
		for (final TaskItemDefine define : taskItemList) {
			taskItemValues.add(Integer.parseInt(define.getTaskItemId()));
		}

		// 得到服务器中待批量处理数据的主键信息(未处理的数据主键)
		final List<Long> ids = dataPrecipitationService.quertIdsBySharding(taskQueueNum, taskItemValues,
				eachFetchDataNum);
		final List<Long> handingIds = ids;

		logger.debug("捞取任务数据:{}.", handingIds);
		if (CollectionUtils.isEmpty(handingIds)) {
			return null;
		}
		// // 数据分片, 分片项为 shardingTotalCount 或 taskExecutor.getCorePoolSize()
		// final int shardingTotal = taskExecutor.getCorePoolSize();
		// final List<List<Long>> lists = CollectionUtil.<Long> split(ids,
		// shardingTotal);
		// logger.debug("批次{}分片项:{}/{}.", batchId, shardingTotal, lists.size());

		return handingIds;
	}

}
