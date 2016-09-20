package com.yueny.demo.dynamic.scheduler.job.dynamic;

import java.util.Map;

import lombok.ToString;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

/**
 * 一个动态的 job 信息
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月28日 下午9:10:01
 *
 */
@ToString
public class DynamicJob {
	/**
	 * cron 表达式
	 */
	private String cronExpression;

	private transient JobDetail jobDetail;

	private String jobGroup = Scheduler.DEFAULT_GROUP;

	/**
	 * 必须唯一. Must unique
	 */
	private String jobName;

	/**
	 * 要执行类, 实现Job接口
	 */
	private Class<? extends Job> target;
	private transient TriggerKey triggerKey;

	/**
	 * default
	 */
	public DynamicJob() {
		// .
	}

	public DynamicJob(final String jobName) {
		this.jobName = jobName;
	}

	/**
	 * Transfer data to job In job use context.getMergedJobDataMap().get(key)<br>
	 * 传参数给 执行的 job 在job中 通过 context.getMergedJobDataMap().get(key) 获取值
	 */
	public DynamicJob addJobData(final String key, final Object value) {
		final JobDetail detail = jobDetail();
		final JobDataMap jobDataMap = detail.getJobDataMap();
		jobDataMap.put(key, value);
		return this;
	}

	/**
	 * Transfer data to job In job use context.getMergedJobDataMap().get(key)<br>
	 * 传参数给 执行的 job 在job中 通过 context.getMergedJobDataMap().get(key) 获取值
	 */
	public DynamicJob addJobDataMap(final Map<String, Object> map) {
		final JobDetail detail = jobDetail();
		final JobDataMap jobDataMap = detail.getJobDataMap();
		jobDataMap.putAll(map);
		return this;
	}

	public String cronExpression() {
		return this.cronExpression;
	}

	public DynamicJob cronExpression(final String cronExpression) {
		this.cronExpression = cronExpression;
		return this;
	}

	public CronTrigger cronTrigger() {
		final CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder
				.cronSchedule(this.cronExpression);
		return TriggerBuilder.newTrigger().withIdentity(triggerKey())
				.withSchedule(cronScheduleBuilder).build();
	}

	public JobDetail jobDetail() {
		if (jobDetail == null) {
			jobDetail = JobBuilder.newJob(target)
					.withIdentity(this.jobName, this.jobGroup).build();
		}
		return jobDetail;
	}

	public String jobGroup() {
		return jobGroup;
	}

	public DynamicJob jobGroup(final String jobGroup) {
		this.jobGroup = jobGroup;
		return this;
	}

	public String jobName() {
		return jobName;
	}

	public DynamicJob jobName(final String jobName) {
		this.jobName = jobName;
		return this;
	}

	public Class<? extends Job> target() {
		return target;
	}

	public DynamicJob target(final Class<? extends Job> target) {
		this.target = target;
		return this;
	}

	public TriggerKey triggerKey() {
		if (triggerKey == null) {
			triggerKey = TriggerKey.triggerKey(this.jobName, this.jobGroup);
		}
		return triggerKey;
	}

}
