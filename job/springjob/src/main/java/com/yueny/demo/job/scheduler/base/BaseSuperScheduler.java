package com.yueny.demo.job.scheduler.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 批任务计划
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年10月31日 下午4:19:26
 *
 */
public abstract class BaseSuperScheduler implements IScheduler {
	/**
	 * 分布式锁的key的前缀
	 */
	protected static final String CACHE_PREFIX = "com_job_scheduler_";

	/**
	 * 日志记录器
	 */
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 组装锁的key
	 *
	 * @param lockKey
	 *            执行'executeLock'时的锁的键值
	 * @return 锁的key
	 */
	protected final String assembleLockKey(final String lockKey) {
		return CACHE_PREFIX + lockKey;
	}

}
