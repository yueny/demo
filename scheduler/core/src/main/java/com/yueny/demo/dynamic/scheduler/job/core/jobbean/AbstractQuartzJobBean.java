package com.yueny.demo.dynamic.scheduler.job.core.jobbean;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 抽象Job 类，如果某个类需要实现定时作业功能，可以继承此类并重写抽象方法
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年12月5日 下午6:10:37
 *
 */
public abstract class AbstractQuartzJobBean implements Job {
	/**
	 * 日志记录器
	 */
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/*
	 * (non-Javadoc)
	 *
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	@Override
	public final void execute(final JobExecutionContext context) throws JobExecutionException {
		before(context);

		scheduler(context);

		after(context);
	}

	/**
	 * 具体执行逻辑交由子类实现
	 *
	 * @param context
	 */
	public abstract void scheduler(final JobExecutionContext context);

	/**
	 * 执行后的操作
	 *
	 * @param context
	 */
	protected void after(final JobExecutionContext context) {
		// .
	}

	/**
	 * 执行前的操作
	 *
	 * @param context
	 */
	protected void before(final JobExecutionContext context) {
		// .
	}

}
