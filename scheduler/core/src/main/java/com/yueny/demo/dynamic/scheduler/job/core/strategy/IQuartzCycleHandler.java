/**
 *
 */
package com.yueny.demo.dynamic.scheduler.job.core.strategy;

import com.yueny.demo.dynamic.scheduler.job.core.DynamicJob;
import com.yueny.demo.dynamic.scheduler.job.core.api.IJobData;
import com.yueny.demo.dynamic.scheduler.job.core.enums.ExpCycleType;
import com.yueny.superclub.util.strategy.IStrategy;

/**
 * 不同任务执行周期的不同添加策略
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年2月9日 下午4:29:47
 * @since
 */
public interface IQuartzCycleHandler extends IStrategy<ExpCycleType> {
	/**
	 * 添加任务
	 *
	 * @param job
	 *            任务对象
	 * @param jobData
	 *            任务附带参数，通过 context.getMergedJobDataMap().get("data")获取
	 * @return 添加成功返回true
	 */
	boolean addJob(DynamicJob job, IJobData jobData);

}
