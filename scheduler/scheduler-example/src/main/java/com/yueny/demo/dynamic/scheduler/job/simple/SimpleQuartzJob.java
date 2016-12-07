package com.yueny.demo.dynamic.scheduler.job.simple;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobKey;

/**
 * example 1
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年12月5日 下午2:13:08
 *
 */
public class SimpleQuartzJob extends AbstractSimpleJavaJobService {
	@Override
	public void scheduler(final JobExecutionContext context) {
		final JobKey jobKey = context.getJobDetail().getKey();

		System.out.println("SimpleQuartzJob says: " + jobKey + " executing at " + new Date() + " by "
				+ context.getTrigger().getDescription());
	}

}
