/**
 *
 */
package com.yueny.demo.dynamic.scheduler.job.core;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;
import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

import com.yueny.demo.dynamic.scheduler.job.core.api.IJob;
import com.yueny.demo.dynamic.scheduler.job.core.factory.DynamicSchedulerManager;
import com.yueny.rapid.lang.date.DateUtil;

import lombok.Getter;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年2月8日 下午2:37:10
 * @since
 */
public abstract class BaseJob implements IJob {
	/**
	 * 任务分组
	 */
	@Getter
	private String jobGroup = DynamicSchedulerManager.DEFAULT_GROUP;
	/**
	 * 必须唯一. Must unique, is identifier， 任务的唯一标识
	 */
	@Getter
	private final String jobName;
	private transient TriggerKey triggerKey;

	public BaseJob(final String jobName, final String jobGroup) {
		this.jobName = jobName;
		this.jobGroup = jobGroup;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.yueny.demo.dynamic.scheduler.job.core.api.IJob#addJobData(java.lang.
	 * String, java.lang.Object)
	 */
	@Override
	public IJob addJobData(final String key, final Object value) {
		final JobDetail detail = getJobDetail();
		final JobDataMap jobDataMap = detail.getJobDataMap();
		jobDataMap.put(key, value);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.yueny.demo.dynamic.scheduler.job.core.api.IJob#addJobDataMap(java.
	 * util.Map)
	 */
	@Override
	public IJob addJobDataMap(final Map<String, Object> map) {
		final JobDetail detail = getJobDetail();
		final JobDataMap jobDataMap = detail.getJobDataMap();
		jobDataMap.putAll(map);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.yueny.demo.dynamic.scheduler.job.core.api.IJob#getTrigger()
	 */
	@Override
	public Trigger getTrigger() {
		try {
			// 如果是指定时间, 不根据时间计算表达式，直接执行
			final Date startTime = DateUtil.parse(getCronExpression());

			final Trigger trigger = newTrigger().withIdentity(getTriggerKey()).startAt(startTime).build();
			return trigger;
		} catch (final IllegalArgumentException e) {
			// 如果是正则表达式
			final CronScheduleBuilder cronScheduleBuilder = cronSchedule(getCronExpression())
					// 所有被misfire的执行会被立即执行，然后按照正常调度继续执行trigger。
					.withMisfireHandlingInstructionIgnoreMisfires();
			final CronTrigger trigger = newTrigger().withIdentity(getTriggerKey()).withSchedule(cronScheduleBuilder)
					.build();
			return trigger;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.yueny.demo.dynamic.scheduler.job.core.api.IJob#getTriggerKey()
	 */
	@Override
	public TriggerKey getTriggerKey() {
		if (triggerKey == null) {
			triggerKey = TriggerKey.triggerKey(getJobName(), getJobGroup());
		}
		return triggerKey;
	}

}
