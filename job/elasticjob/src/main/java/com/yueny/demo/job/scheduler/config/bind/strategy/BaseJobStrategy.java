package com.yueny.demo.job.scheduler.config.bind.strategy;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.JobTypeConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.google.common.base.Preconditions;
import com.yueny.demo.job.scheduler.config.bind.JopType;

import lombok.Getter;

public abstract class BaseJobStrategy implements IJobStrategy, ApplicationContextAware {
	private static ApplicationContext context;
	@Resource
	@Getter
	private JobEventConfiguration jobEventConfiguration;
	@Resource
	@Getter
	private ZookeeperRegistryCenter regCenter;
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public ApplicationContext getContext() {
		Preconditions.checkArgument(BaseJobStrategy.context != null, "application未注入!");
		return BaseJobStrategy.context;
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) {
		BaseJobStrategy.context = applicationContext;
	}

	/**
	 * 定义作业核心配置
	 */
	protected LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass,
			final JopType jopType, final String cron, final int shardingTotalCount,
			final String shardingItemParameters) {
		final JobCoreConfiguration coreConfig = JobCoreConfiguration
				.newBuilder(jobClass.getCanonicalName(), cron, shardingTotalCount)
				.shardingItemParameters(shardingItemParameters).build();

		JobTypeConfiguration jobTypeConfiguration = null;
		if (jopType == JopType.SIMPLE) {
			// 定义SIMPLE类型配置
			jobTypeConfiguration = new SimpleJobConfiguration(coreConfig, jobClass.getCanonicalName());
		} else if (jopType == JopType.DATAFLOW) {
			jobTypeConfiguration = new DataflowJobConfiguration(coreConfig, jobClass.getCanonicalName(), false);
		}

		// 定义Lite作业根配置
		return LiteJobConfiguration.newBuilder(jobTypeConfiguration).overwrite(true).build();
	}

}
