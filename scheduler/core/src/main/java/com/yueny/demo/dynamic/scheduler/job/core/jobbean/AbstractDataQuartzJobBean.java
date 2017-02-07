package com.yueny.demo.dynamic.scheduler.job.core.jobbean;

import com.yueny.demo.dynamic.scheduler.job.core.api.IJobData;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractDataQuartzJobBean<T extends IJobData> extends AbstractQuartzJobBean {

	/**
	 * */
	@Getter
	@Setter
	private T data;

}
