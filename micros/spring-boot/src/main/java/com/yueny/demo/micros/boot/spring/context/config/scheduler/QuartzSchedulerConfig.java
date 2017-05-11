package com.yueny.demo.micros.boot.spring.context.config.scheduler;

import javax.sql.DataSource;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.yueny.demo.dynamic.scheduler.job.core.listener.QuartzJobListener;
import com.yueny.demo.micros.boot.spring.factory.autowired.AutowiringSpringBeanJobFactory;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 注册spring-boot启动完成事件监听，用于启动job任务
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年4月26日 下午3:13:27
 *
 */
// @Configuration
// @ConfigurationProperties(prefix = "spring.scheduler.quartz.factory")
@Slf4j
public class QuartzSchedulerConfig implements ApplicationListener<ContextRefreshedEvent> {
	@Setter
	private String applicationContextSchedulerContextKey = "appli";
	@Setter
	private boolean autoStartup = true;
	/**
	 * 每台集群机器部署应用的时候会更新触发器
	 */
	@Setter
	private boolean overwriteExistingJobs = true;

	@Autowired
	@Qualifier("quartzDataSource")
	private DataSource quartzDataSource;
	/**
	 * 任务唯一的名称，将会持久化到数据库
	 */
	@Setter
	private String schedulerName = "defaultQuartzScheduler";

	@Override
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		log.info("spring-boot启动完成，开始进行任务调度...");
	}

	@Bean
	public QuartzJobListener quartzJobListener() {
		final QuartzJobListener quartzJobListener = new QuartzJobListener();
		return quartzJobListener;
	}

	@Bean
	public Scheduler scheduler() {
		return schedulerFactoryBean().getScheduler();
	}

	@Bean(destroyMethod = "destroy")
	public SchedulerFactoryBean schedulerFactoryBean() {
		final ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

		final SchedulerFactoryBean quartzScheduler = new SchedulerFactoryBean();
		quartzScheduler.setConfigLocation(resolver.getResource("classpath:/properties/quartz.properties"));
		quartzScheduler.setDataSource(quartzDataSource);
		//
		// schedulerFactoryBean.setTransactionManager(quartzTransactionManager);
		// 延时启动
		quartzScheduler.setStartupDelay(10);
		quartzScheduler.setSchedulerName(schedulerName);
		quartzScheduler.setOverwriteExistingJobs(overwriteExistingJobs);
		// quartzScheduler.setApplicationContextSchedulerContextKey(applicationContextSchedulerContextKey);
		// 自定义Job Factory，用于Spring注入
		quartzScheduler.setJobFactory(new AutowiringSpringBeanJobFactory());
		quartzScheduler.setAutoStartup(autoStartup);

		return quartzScheduler;
	}

	// @Bean(name = "dynamicSchedulerManager")
	// public DynamicSchedulerManager schedulerManager() {
	// final DynamicSchedulerManager dynamicSchedulerManager = new
	// DynamicSchedulerManager();
	// dynamicSchedulerManager.setScheduler(scheduler());
	// dynamicSchedulerManager.setListener(quartzJobListener);
	//
	// return dynamicSchedulerManager;
	// }

}
