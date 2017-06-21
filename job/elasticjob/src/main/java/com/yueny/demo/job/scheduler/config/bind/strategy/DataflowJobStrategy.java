package com.yueny.demo.job.scheduler.config.bind.strategy;

import org.springframework.stereotype.Service;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.yueny.demo.job.scheduler.config.bind.JobBean;
import com.yueny.demo.job.scheduler.config.bind.JopType;

/**
 * <job:dataflow id="${dataflow.id}" class="${dataflow.class}"
 * registry-center-ref="regCenter" sharding-total-count=
 * "${dataflow.shardingTotalCount}" cron="${dataflow.cron}"
 * sharding-item-parameters="${dataflow.shardingItemParameters}"
 * monitor-execution="${dataflow.monitorExecution}" failover=
 * "${dataflow.failover}" max-time-diff-seconds="${dataflow.maxTimeDiffSeconds}"
 * streaming-process="${dataflow.streamingProcess}" description=
 * "${dataflow.description}" disabled="${dataflow.disabled}" overwrite=
 * "${dataflow.overwrite}" />
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年6月20日 下午10:48:10
 *
 */
@Service
public class DataflowJobStrategy extends BaseJobStrategy {
	// private final ElasticJobListener jobListener = new SimpleListener();

	@Override
	public JopType getCondition() {
		return JopType.DATAFLOW;
	}

	@Override
	public JobScheduler jobScheduler(final JobBean jobBean) {
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
		// 判断一个类clazz和另一个类SimpleJob是否相同或是另一个类的超类或接口。 父-->子
		if (!SimpleJob.class.isAssignableFrom(dataflowJobObj)) {
			System.out.println("暂不支持的" + getCondition() + "服务类类型!");
		} else {
			final Class<? extends SimpleJob> dataflowJobClazz = (Class<? extends SimpleJob>) dataflowJobObj;

			final SimpleJob dataflowJob = getContext().getBean(dataflowJobClazz);
			jobScheduler = new SpringJobScheduler(dataflowJob, getRegCenter(),
					getLiteJobConfiguration(dataflowJobClazz, jobBean), getJobEventConfiguration());
		}
		return jobScheduler;
	}

}
