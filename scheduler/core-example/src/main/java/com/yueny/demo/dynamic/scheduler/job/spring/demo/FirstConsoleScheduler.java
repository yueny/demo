package com.yueny.demo.dynamic.scheduler.job.spring.demo;

import org.quartz.JobExecutionContext;

import com.yueny.demo.dynamic.scheduler.job.core.jobbean.AbstractQuartzJobBean;

/**
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2015年12月10日 下午5:47:46
 *
 */
public class FirstConsoleScheduler extends AbstractQuartzJobBean {
	@Override
	public void scheduler(final JobExecutionContext context) {
		System.out.println("I am FirstConsoleScheduler");
	}
}
