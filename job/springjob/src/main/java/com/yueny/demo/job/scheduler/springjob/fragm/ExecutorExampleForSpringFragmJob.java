package com.yueny.demo.job.scheduler.springjob.fragm;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.yueny.demo.job.scheduler.base.BaseSuperScheduler;
import com.yueny.demo.job.service.IDataPrecipitationService;
import com.yueny.rapid.lang.lua.lock.IDistributedLock;
import com.yueny.rapid.lang.util.time.SystemClock;
import com.yueny.superclub.util.exec.async.factory.ExecutorServiceObjectFactory;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年4月18日 下午6:26:57
 *
 */
@Service
public class ExecutorExampleForSpringFragmJob extends BaseSuperScheduler {
	@Autowired
	private IDataPrecipitationService dataPrecipitationService;
	@Autowired
	private IDistributedLock redisLock;
	@Autowired
	private IDistributedLock redisLuaLock;

	final ExecutorService executorService = ExecutorServiceObjectFactory
			.getExecutorServiceObject("executor-asyn-example").createExecutorService();

	/**
	 *
	 */
	@Scheduled(cron = "0/8 * * * * ?")
	public void processData() {
		if (redisLock.tryLock(assembleLockKey("_PRECIPITATION_RUN"), 1)) {
			try {
				final long start = SystemClock.now();

				// 得到服务器中待批量处理数据的主键信息, limit 50
				final List<Long> ids = dataPrecipitationService.queryAllIds();

				if (CollectionUtils.isEmpty(ids)) {
					return;
				}

				try {
					TimeUnit.MILLISECONDS.sleep(1000);
				} catch (final InterruptedException e) {
					e.printStackTrace();
				}

				final long end = SystemClock.now();
				logger.info("ExecutorExampleForSpringFragmJob end:  总耗时:{}秒.", (end - start) / 1000);
			} finally {
				redisLock.unlock(assembleLockKey("_PRECIPITATION_RUN"));
			}
		} else {
			logger.info("未获得同步锁,跳过!");
		}
	}

}
