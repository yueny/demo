package com.yueny.demo.dynamic.scheduler.job.core.strategy;

import org.springframework.stereotype.Service;

import com.yueny.demo.dynamic.scheduler.job.core.DynamicJob;
import com.yueny.demo.dynamic.scheduler.job.core.api.IJobData;
import com.yueny.demo.dynamic.scheduler.job.core.enums.ExpCycleType;

/**
 * cron表达式
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年2月9日 下午4:34:28
 * @since
 */
@Service
public class CronQuartzCycleHandler extends AbstractQuartzCycleHandler {
	@Override
	public boolean addJob(DynamicJob job, IJobData jobData) {
		job.addJobData(JOB_DATA_KEY, jobData);
		return getSchedulerManager().registerJob(job);
	}

	@Override
	public ExpCycleType getCondition() {
		return ExpCycleType.CRON;
	}
}
