package com.yueny.demo.job.scheduler.elasticjob;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.google.common.base.Preconditions;
import com.yueny.demo.job.scheduler.config.bind.JobBean;
import com.yueny.demo.job.scheduler.config.bind.JobsConfigurationLoader;
import com.yueny.demo.job.scheduler.config.bind.JopType;
import com.yueny.demo.job.scheduler.config.bind.strategy.IJobStrategy;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年6月13日 下午4:23:32
 *
 */
@Configuration
public class JobConfigLoader implements InitializingBean, ApplicationContextAware {
	private final Map<JopType, IJobStrategy> container = new HashMap<JopType, IJobStrategy>();
	private ApplicationContext context;
	@Resource
	private JobEventConfiguration jobEventConfiguration;
	@Resource
	private ZookeeperRegistryCenter regCenter;

	@Override
	public void afterPropertiesSet() throws Exception {
		final Map<String, IJobStrategy> maps = getContext().getBeansOfType(IJobStrategy.class);
		for (final IJobStrategy jobStrategy : maps.values()) {
			container.put(jobStrategy.getCondition(), jobStrategy);
		}

		// TODO 后续以异步线程方式实现
		jobSchedulerLoader();
	}

	public ApplicationContext getContext() {
		Preconditions.checkArgument(context != null, "application未注入!");
		return context;
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) {
		context = applicationContext;
	}

	private IJobStrategy getStrategy(final JopType jopType) {
		final IJobStrategy jobStrategy = container.get(jopType);
		if (jobStrategy == null) {
			return null;
		}
		return jobStrategy;
	}

	private boolean jobSchedulerLoader() {
		// final SimpleJob demoSimpleJob, @Value("${simpleJob.cron}") final
		// String cron,
		// @Value("${simpleJob.shardingTotalCount}") final int
		// shardingTotalCount,
		// @Value("${simpleJob.shardingItemParameters}") final String
		// shardingItemParameters

		// // 启动作业
		// new JobScheduler(regCenter,
		// BaseJobStrategy.getLiteJobConfiguration(DemoSimpleJob.class,
		// JopType.SIMPLE, "0/10 * * * * ?", 1, "0=Beijing"),
		// jobEventConfiguration).init();

		// 使用Spring配置启动
		for (final JobBean jobBean : JobsConfigurationLoader.getJobs()) {
			final IJobStrategy jobStrategy = getStrategy(jobBean.getType());
			if (jobStrategy != null) {
				final JobScheduler jobScheduler = jobStrategy.jobScheduler(jobBean);
				if (jobScheduler != null) {
					jobScheduler.init();
				}
			}
		}

		return true;
	}

}
