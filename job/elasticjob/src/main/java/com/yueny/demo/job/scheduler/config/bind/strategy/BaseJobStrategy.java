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
import com.yueny.demo.job.scheduler.config.bind.JobBean;
import com.yueny.demo.job.scheduler.config.bind.JopType;
import com.yueny.rapid.lang.util.StringUtil;

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
			final JobBean jobBean) {
		final JopType jopType = jobBean.getType();
		final String cron = jobBean.getCron();
		final int shardingTotalCount = jobBean.getShardingTotalCount();
		final String shardingItemParameters = jobBean.getShardingItemParameters();

		final JobCoreConfiguration coreConfig = JobCoreConfiguration
				.newBuilder(jobClass.getCanonicalName(), cron, shardingTotalCount)
				.shardingItemParameters(shardingItemParameters)
				// 是否开启misfire
				.misfire(true)
				// 是否开启失效转移
				.failover(jobBean.isFailover())
				// 作业描述信息
				.description(StringUtil.isEmpty(jobBean.getDescription()) ? jobClass.getCanonicalName()
						: jobBean.getDescription())
				.build();

		JobTypeConfiguration jobTypeConfiguration = null;
		if (jopType == JopType.SIMPLE) {
			// 定义SIMPLE类型配置
			jobTypeConfiguration = new SimpleJobConfiguration(coreConfig, jobClass.getCanonicalName());
		} else if (jopType == JopType.DATAFLOW) {
			jobTypeConfiguration = new DataflowJobConfiguration(coreConfig, jobClass.getCanonicalName(), true);
		}

		// 定义Lite作业根配置
		return LiteJobConfiguration.newBuilder(jobTypeConfiguration)
				// 本地配置是否可覆盖注册中心配置
				.overwrite(jobBean.isOverwrite())
				// 作业是否启动时禁止
				.disabled(jobBean.isDisabled())
				/*
				 * 监控作业执行时状态
				 *
				 * 开启monitorExecution才能实现分布式作业幂等性（即不会在多个作业服务器运行同一个分片）的功能，
				 * 但monitorExecution对短时间内执行的作业（如每5秒一触发）性能影响较大，建议关闭并自行实现幂等性。
				 */
				.monitorExecution(jobBean.isMonitorExecution())
				// 作业辅助监控端口，如 9888，便于dump命令作业
				// eg: echo "dump" | nc <任意一台作业服务器IP> 9888
				// eg: echo "dump" | nc <任意一台作业服务器IP> 9888 > job_debug.txt
				// .monitorPort(monitorPort)
				.build();
	}

}
