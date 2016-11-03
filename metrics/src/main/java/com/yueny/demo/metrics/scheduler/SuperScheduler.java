package com.yueny.demo.metrics.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 批任务处理
 * 
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年10月31日 下午4:19:26
 *
 */
public abstract class SuperScheduler implements IScheduler {
	/**
	 * 日志记录器
	 */
	protected final Logger logger = LoggerFactory.getLogger(getClass());
}
