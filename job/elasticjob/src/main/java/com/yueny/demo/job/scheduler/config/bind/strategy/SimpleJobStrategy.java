package com.yueny.demo.job.scheduler.config.bind.strategy;

import org.springframework.stereotype.Service;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.yueny.demo.job.scheduler.config.bind.JobBean;
import com.yueny.demo.job.scheduler.config.bind.JopType;

@Service
public class SimpleJobStrategy extends BaseJobStrategy {
	@Override
	public JopType getCondition() {
		return JopType.SIMPLE;
	}

	@Override
	public JobScheduler jobScheduler(final JobBean jobBean) {
		JobScheduler jobScheduler = null;
		try {
			final Class<?> clazz = Class.forName(jobBean.getName());

			// 判断一个类clazz和另一个类SimpleJob是否相同或是另一个类的超类或接口。 父-->子
			if (!SimpleJob.class.isAssignableFrom(clazz)) {
				System.out.println("暂不支持的" + getCondition() + "服务类类型!");
			} else {
				final Class<? extends SimpleJob> clazzSimple = (Class<? extends SimpleJob>) clazz;

				final SimpleJob job = getContext().getBean(clazzSimple);
				jobScheduler = new SpringJobScheduler(job, getRegCenter(),
						getLiteJobConfiguration(clazzSimple, jobBean.getType(), jobBean.getCron(),
								jobBean.getShardingTotalCount(), jobBean.getShardingItemParameters()),
						getJobEventConfiguration());
			}
		} catch (final ClassNotFoundException e) {
			e.printStackTrace();
			logger.info("服务添加异常！", e);
		}
		return jobScheduler;
	}

}
