package com.yueny.demo.job.scheduler.elasticjob;

import java.util.HashMap;
import java.util.List;
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
import com.google.common.collect.Lists;
import com.yueny.demo.job.scheduler.config.bind.JobsConfigurationLoader;
import com.yueny.demo.job.scheduler.config.bind.JopType;
import com.yueny.demo.job.scheduler.config.bind.model.IJobBean;
import com.yueny.demo.job.scheduler.config.bind.strategy.IJobStrategy;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年6月13日 下午4:23:32
 *
 */
@Configuration
@Slf4j
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
		// 使用Spring配置启动
		final List<IJobBean> jobBeans = Lists.newArrayList();
		jobBeans.addAll(JobsConfigurationLoader.getJobs());
		jobBeans.addAll(JobsConfigurationLoader.getDataflowJobs());

		for (final IJobBean jobBean : jobBeans) {
			final IJobStrategy jobStrategy = getStrategy(jobBean.getType());
			if (jobStrategy != null) {
				try {
					final JobScheduler jobScheduler = jobStrategy.jobScheduler(jobBean);
					if (jobScheduler != null) {
						jobScheduler.init();
					}
				} catch (final Exception ignore) {
					log.warn("任务{}初始化时异常，初始化操作跳过。", jobBean);
				}
			}
		}

		return true;
	}

}
