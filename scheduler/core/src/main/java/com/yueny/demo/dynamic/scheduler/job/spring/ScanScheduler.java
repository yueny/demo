package com.yueny.demo.dynamic.scheduler.job.spring;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.yueny.demo.dynamic.scheduler.job.factory.DynamicSchedulerFactory;

/**
 * 无用文件扫描并删除任务
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2015年12月10日 下午5:47:46
 *
 */
// @PersistJobDataAfterExecution
// @DisallowConcurrentExecution//保证多个任务间不会同时执行.所以在多任务执行时最好加上
public class ScanScheduler extends QuartzJobBean {
	/** 日志 */
	private static final Logger logger = LoggerFactory.getLogger(ScanScheduler.class);
	// 这里就是因为有上文中的AutowiringSpringBeanJobFactory才可以使用@Autowired注解，否则只能在配置文件中设置这属性的值
	@Autowired
	private DynamicSchedulerFactory dynamicSchedulerFactory;

	/* 业务实现 */
	public void work() {
		System.out.println("==============================================");
		logger.info("I am working doSomething on {}.", dynamicSchedulerFactory);
		System.out.println("==============================================");
	}

	@Override
	protected void executeInternal(final JobExecutionContext context) throws JobExecutionException {
		final JobKey key = context.getJobDetail().getKey();
		logger.info("执行调度任务 execute Job {}", key);

		// final JobDataMap data = context.getJobDetail().getJobDataMap();

		work();
	}
}
