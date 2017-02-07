package com.yueny.demo.dynamic.scheduler.job.core.invok;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

import com.yueny.demo.dynamic.scheduler.job.core.DynamicInvokJob;

/**
 * 若一个方法一次执行不完下次轮转时则等待改方法执行完后才执行下一次操作
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年2月7日 下午6:27:00
 */
@DisallowConcurrentExecution
public class QuartzJobFactoryDisallowConcurrentExecution extends AbstractQuartzinvokJobBean {

	@Override
	public void scheduler(final JobExecutionContext context) {
		final DynamicInvokJob scheduleJob = (DynamicInvokJob) context.getMergedJobDataMap().get("dynamicInvokJob");
		this.invokMethod(scheduleJob);
	}

}
