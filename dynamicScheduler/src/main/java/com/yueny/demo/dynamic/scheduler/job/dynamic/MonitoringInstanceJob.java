package com.yueny.demo.dynamic.scheduler.job.dynamic;

import lombok.extern.slf4j.Slf4j;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

/**
 * The Job is concurrently
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月29日 上午12:20:19
 *
 */
@DisallowConcurrentExecution
@Slf4j
public class MonitoringInstanceJob implements Job {
	public MonitoringInstanceJob() {
		// .
	}

	@Override
	public void execute(final JobExecutionContext context)
			throws JobExecutionException {
		final JobKey key = context.getJobDetail().getKey();
		log.info("*****  Start execute Job [{}]", key);

		log.info("&&&&&  End execute Job [{}]", key);
	}

}