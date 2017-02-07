package com.yueny.demo.dynamic.scheduler.job.core.invok;

import org.quartz.JobExecutionContext;

import com.yueny.demo.dynamic.scheduler.job.core.DynamicInvokJob;

/**
 * 计划任务执行处 无状态
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年2月7日 下午6:27:00
 *
 */
public class QuartzJobFactoryExecution extends AbstractQuartzinvokJobBean {

	@Override
	public void scheduler(final JobExecutionContext context) {
		final DynamicInvokJob scheduleJob = (DynamicInvokJob) context.getMergedJobDataMap().get("dynamicInvokJob");
		this.invokMethod(scheduleJob);
	}

}
