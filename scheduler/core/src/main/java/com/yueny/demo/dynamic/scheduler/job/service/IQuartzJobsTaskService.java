package com.yueny.demo.dynamic.scheduler.job.service;

import java.util.List;

import com.yueny.demo.dynamic.scheduler.job.bo.QuartzJobsTaskBo;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月5日 上午9:56:50
 *
 */
public interface IQuartzJobsTaskService {
	/**
	 * @return
	 */
	List<QuartzJobsTaskBo> queryAll();

}
