package com.yueny.demo.dynamic.scheduler.job.core.model;

import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import com.yueny.demo.dynamic.scheduler.job.core.factory.DynamicSchedulerFactory;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 一个动态的 job 信息
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月28日 下午9:10:01
 *
 */
/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年2月7日 下午3:19:52
 *
 */
@ToString
@NoArgsConstructor
public class DynamicJob {
	/**
	 * cron 表达式
	 */
	@Getter
	private String cronExpression;

	private transient JobDetail jobDetail;
	@Getter
	private String jobGroup = DynamicSchedulerFactory.DEFAULT_GROUP;

	/**
	 * 必须唯一. Must unique, is identifier， 任务的唯一标识
	 */
	@Getter
	private String jobName;
	/**
	 * 要执行类, 实现Job接口
	 */
	@Getter
	private Class<? extends Job> target;

	private transient TriggerKey triggerKey;

	// // /**
	// // * default
	// // */
	// public DynamicJob(final String jobName) {
	// this.jobName = jobName;
	// }

	/**
	 * Transfer data to job In job use context.getMergedJobDataMap().get(key)
	 * <br>
	 * 传参数给 执行的 job 在job中 通过 context.getMergedJobDataMap().get(key) 获取值
	 */
	public DynamicJob addJobData(final String key, final Object value) {
		final JobDetail detail = getJobDetail();
		final JobDataMap jobDataMap = detail.getJobDataMap();
		jobDataMap.put(key, value);
		return this;
	}

	/**
	 * Transfer data to job In job use context.getMergedJobDataMap().get(key)
	 * <br>
	 * 传参数给 执行的 job 在job中 通过 context.getMergedJobDataMap().get(key) 获取值
	 */
	public DynamicJob addJobDataMap(final Map<String, Object> map) {
		final JobDetail detail = getJobDetail();
		final JobDataMap jobDataMap = detail.getJobDataMap();
		jobDataMap.putAll(map);
		return this;
	}

	public DynamicJob cronExpression(final String cronExpression) {
		this.cronExpression = cronExpression;
		return this;
	}

	/**
	 * @return 获取CronTrigger触发器
	 */
	public CronTrigger cronTrigger() {
		final CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(this.cronExpression);
		return TriggerBuilder.newTrigger().withIdentity(getTriggerKey()).withSchedule(cronScheduleBuilder).build();
	}

	public JobDetail getJobDetail() {
		if (jobDetail == null) {
			jobDetail = JobBuilder.newJob(target).withIdentity(this.jobName, this.jobGroup).build();
		}
		return jobDetail;
	}

	/**
	 * @return 获取TriggerKey
	 */
	public TriggerKey getTriggerKey() {
		if (triggerKey == null) {
			triggerKey = TriggerKey.triggerKey(this.jobName, this.jobGroup);
		}
		return triggerKey;
	}

	public DynamicJob jobGroup(final String jobGroup) {
		this.jobGroup = jobGroup;
		return this;
	}

	public DynamicJob jobName(final String jobName) {
		this.jobName = jobName;
		return this;
	}

	public DynamicJob target(final Class<? extends Job> target) {
		this.target = target;
		return this;
	}

}
