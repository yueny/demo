package com.yueny.demo.job.scheduler.elasticjob.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.yueny.demo.job.scheduler.base.IScheduler;

public abstract class AbstractSimpleJob implements SimpleJob, IScheduler {
	/**
	 * 日志记录器
	 */
	protected static final Logger logger = LoggerFactory.getLogger(AbstractSimpleJob.class);

	@Override
	public final void execute(final ShardingContext shardingContext) {
		try {
			executor(shardingContext);
		} catch (final Exception e) {
			logger.error("exception: ", e);
		}
	}

	public abstract void executor(final ShardingContext shardingContext);

}
