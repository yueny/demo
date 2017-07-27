package com.yueny.demo.job.scheduler.config.bind.strategy;

import org.springframework.stereotype.Service;

import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.yueny.demo.job.scheduler.config.bind.JopType;
import com.yueny.demo.job.scheduler.config.bind.model.IJobBean;

/**
 * 流式任务实例化策略
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年6月20日 下午10:48:10
 *
 */
@Service
public class DataflowJobStrategy extends BaseJobStrategy {
	@Override
	public JopType getCondition() {
		return JopType.DATAFLOW;
	}

	@Override
	public JobScheduler jobScheduler(final IJobBean jobBean) {
		Class<?> dataflowJobObj = null;
		try {
			dataflowJobObj = Class.forName(jobBean.getName());
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
			logger.info("服务添加异常！", e);
		}

		if (dataflowJobObj == null) {
			return null;
		}

		JobScheduler jobScheduler = null;
		if (!DataflowJob.class.isAssignableFrom(dataflowJobObj)) {
			System.out.println("暂不支持的" + getCondition() + "服务类DataflowJob类型!");
		} else {
			final Class<? extends DataflowJob<?>> dataflowJobClazz = (Class<? extends DataflowJob<?>>) dataflowJobObj;

			final long startTimeoutMills = 5000L;
			final long completeTimeoutMills = 10000L;

			final DataflowJob<?> dataflowJob = getContext().getBean(dataflowJobClazz);
			jobScheduler = new SpringJobScheduler(dataflowJob, getRegCenter(),
					getLiteJobConfiguration(dataflowJobClazz, jobBean), getJobEventConfiguration()
			// new SimpleJobListener()
			// new DistributeOnceJobListener(startTimeoutMills,
			// completeTimeoutMills)
			);
		}
		return jobScheduler;
	}

}
