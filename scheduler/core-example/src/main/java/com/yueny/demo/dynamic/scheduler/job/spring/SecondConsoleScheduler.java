package com.yueny.demo.dynamic.scheduler.job.spring;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2015年12月10日 下午5:47:46
 *
 */
public class SecondConsoleScheduler extends QuartzJobBean {
	@Override
	protected void executeInternal(final JobExecutionContext context) throws JobExecutionException {
		System.out.println("I am SecondConsoleScheduler");
	}
}
