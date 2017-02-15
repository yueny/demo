package com.yueny.demo.dynamic.scheduler.job.core.strategy;

import org.springframework.stereotype.Service;

import com.yueny.demo.dynamic.scheduler.job.core.DynamicJob;
import com.yueny.demo.dynamic.scheduler.job.core.api.IJobData;
import com.yueny.demo.dynamic.scheduler.job.core.enums.ExpCycleType;

/**
 * 每天执行任务
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年2月9日 下午5:44:20
 * @since
 */
@Service
public class DayQuartzCycleHandler extends AbstractQuartzCycleHandler {

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ExpCycleType getCondition() {
		return ExpCycleType.DAY;
	}
}
