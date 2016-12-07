package com.yueny.demo.dynamic.scheduler.job.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yueny.demo.dynamic.scheduler.job.factory.DynamicSchedulerFactory;

/**
 * 无用文件扫描并删除任务<br>
 * org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean方式加载
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2015年12月10日 下午5:47:46
 *
 */
public class ScanWithMethodInvokingScheduler {
	/** 日志 */
	private static final Logger logger = LoggerFactory.getLogger(ScanWithMethodInvokingScheduler.class);
	@Autowired
	private DynamicSchedulerFactory dynamicSchedulerFactory;

	public void doSomething() {
		// you'll also notice that we cannot use
		// MethodInvokingJobDetailFactoryBean because it is not serializable ,
		// so we need to create our own Job class that extends QuartzJobBean.
		logger.info("scanScheduler ...");
		System.out.println("I am working doSomething on " + dynamicSchedulerFactory);
	}
}
