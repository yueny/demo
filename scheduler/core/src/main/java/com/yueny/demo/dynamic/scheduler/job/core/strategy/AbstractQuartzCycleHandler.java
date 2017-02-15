package com.yueny.demo.dynamic.scheduler.job.core.strategy;

import org.springframework.beans.factory.annotation.Autowired;

import com.yueny.demo.dynamic.scheduler.job.core.factory.DynamicSchedulerManager;

/**
 * Created by chars on 2017/1/19 15:32
 *
 * @since 1.6.0
 */
public abstract class AbstractQuartzCycleHandler implements IQuartzCycleHandler {
	@Autowired
	private DynamicSchedulerManager dynamicSchedulerManager;

	public DynamicSchedulerManager getSchedulerManager() {
		return dynamicSchedulerManager;
	}
}
