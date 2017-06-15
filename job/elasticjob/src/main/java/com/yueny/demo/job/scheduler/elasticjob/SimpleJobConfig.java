package com.yueny.demo.job.scheduler.elasticjob;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.yueny.demo.job.scheduler.elasticjob.simple.SpringDemoSimpleJob;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年6月13日 下午4:23:32
 *
 */
@Configuration
public class SimpleJobConfig implements InitializingBean {
	@Resource
	private JobEventConfiguration jobEventConfiguration;
	@Resource
	private ZookeeperRegistryCenter regCenter;
	@Resource
	private SpringDemoSimpleJob springDemoSimpleJob;

	@Override
	public void afterPropertiesSet() throws Exception {
		jobSchedulerLoader();
	}

	private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass, final String cron,
			final int shardingTotalCount, final String shardingItemParameters) {
		final JobCoreConfiguration coreConfig = JobCoreConfiguration
				.newBuilder(
						// jobClass.getCanonicalName()
						jobClass.getName(), cron, shardingTotalCount)
				.shardingItemParameters(shardingItemParameters).build();

		return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(coreConfig, jobClass.getCanonicalName()))
				.overwrite(true).build();
	}

	private boolean jobSchedulerLoader() {
		// final SimpleJob demoSimpleJob, @Value("${simpleJob.cron}") final
		// String cron,
		// @Value("${simpleJob.shardingTotalCount}") final int
		// shardingTotalCount,
		// @Value("${simpleJob.shardingItemParameters}") final String
		// shardingItemParameters
		final String cron = "0/10 * * * * ?";
		final int shardingTotalCount = 2;
		final String shardingItemParameters = "0=Beijing,1=Shanghai,2=Guangzhou";

		// new JobScheduler(regCenter,
		// getLiteJobConfiguration(SpringDemoSimpleJob.class, cron,
		// shardingTotalCount, shardingItemParameters),
		// jobEventConfiguration).init();
		new SpringJobScheduler(springDemoSimpleJob, regCenter, getLiteJobConfiguration(springDemoSimpleJob.getClass(),
				cron, shardingTotalCount, shardingItemParameters), jobEventConfiguration).init();

		return true;
	}

}
