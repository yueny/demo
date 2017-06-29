package com.yueny.demo.job.scheduler.config.bind.model;

/**
 * 流式任务对象
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年6月23日 下午4:54:05
 *
 */
public interface IDataflowJobBean extends IJobBean {
	/**
	 * maxTimeDiffSeconds
	 */
	int getMaxTimeDiffSeconds();

	/**
	 * 是否为流式处理作业，默认为是
	 */
	boolean isStreamingProcess();

}
