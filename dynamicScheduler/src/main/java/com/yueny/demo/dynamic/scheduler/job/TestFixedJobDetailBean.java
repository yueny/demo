package com.yueny.demo.dynamic.scheduler.job;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.yueny.demo.dynamic.scheduler.job.dynamic.DynamicSchedulerFactory;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月28日 下午8:48:31
 *
 */
public class TestFixedJobDetailBean extends QuartzJobBean {
	@Autowired
	private DynamicSchedulerFactory dynamicSchedulerFactory;

	@Override
	protected void executeInternal(final JobExecutionContext context)
			throws JobExecutionException {
		System.out.println("dynamicSchedulerFactory is :"
				+ dynamicSchedulerFactory);
		System.out.println("I am working on " + new Date());
	}

}
