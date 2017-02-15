package com.yueny.demo.dynamic.scheduler.job.core.strategy;

import org.springframework.stereotype.Service;

import com.yueny.demo.dynamic.scheduler.job.core.DynamicJob;
import com.yueny.demo.dynamic.scheduler.job.core.api.IJobData;
import com.yueny.demo.dynamic.scheduler.job.core.enums.ExpCycleType;

/**
 * 执行一次的任务
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年2月9日 下午5:36:51
 * @since
 */
@Service
public class OnceQuartzCycleHandler extends AbstractQuartzCycleHandler {

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.yueny.demo.dynamic.scheduler.job.core.strategy.IQuartzCycleHandler#
	 * addJob(com.yueny.demo.dynamic.scheduler.job.core.DynamicJob,
	 * com.yueny.demo.dynamic.scheduler.job.core.api.IJobData)
	 */
	@Override
	public boolean addJob(DynamicJob job, IJobData jobData) {
		job.addJobData(JOB_DATA_KEY, jobData);
		return getSchedulerManager().registerJob(job);
	}

	@Override
	public ExpCycleType getCondition() {
		return ExpCycleType.ONCE;
	}
}
