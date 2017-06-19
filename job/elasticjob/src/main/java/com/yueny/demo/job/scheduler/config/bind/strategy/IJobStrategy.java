package com.yueny.demo.job.scheduler.config.bind.strategy;

import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.yueny.demo.job.scheduler.config.bind.JobBean;
import com.yueny.demo.job.scheduler.config.bind.JopType;
import com.yueny.superclub.util.strategy.IStrategy;

public interface IJobStrategy extends IStrategy<JopType> {
	JobScheduler jobScheduler(JobBean jobBean);

}
