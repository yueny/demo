package com.yueny.demo.dynamic.scheduler.job.core;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.TriggerKey;

import com.yueny.demo.dynamic.scheduler.job.core.factory.DynamicSchedulerFactory;
import com.yueny.demo.dynamic.scheduler.job.core.invok.QuartzJobFactoryDisallowConcurrentExecution;
import com.yueny.demo.dynamic.scheduler.job.core.invok.QuartzJobFactoryExecution;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 一个动态的 job 信息
 * 
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年2月7日 下午3:19:52
 *
 */
@ToString
@NoArgsConstructor
public class DynamicInvokJob {
	@ToString
	public static class DynamicInvokJobBuilder {
		private String cronExpression;
		/**
		 * 任务是否有状态 ， 默认无状态
		 */
		private boolean isConcurrent = false;
		private String jobGroup;
		private String jobName;
		private String methodName;
		private String target;

		private DynamicInvokJobBuilder() {
			// .
		}

		public DynamicInvokJob build() {
			return new DynamicInvokJob(jobName, target, methodName, cronExpression, jobGroup, isConcurrent);
		}

		public DynamicInvokJobBuilder cronExpression(final String cronExpression) {
			this.cronExpression = cronExpression;
			return this;
		}

		public DynamicInvokJobBuilder isConcurrent(final boolean isConcurrent) {
			this.isConcurrent = isConcurrent;
			return this;
		}

		public DynamicInvokJobBuilder jobGroup(final String jobGroup) {
			this.jobGroup = jobGroup;
			return this;
		}

		public DynamicInvokJobBuilder jobName(final String jobName) {
			this.jobName = jobName;
			return this;
		}

		public DynamicInvokJobBuilder methodName(final String methodName) {
			this.methodName = methodName;
			return this;
		}

		public DynamicInvokJobBuilder target(final String target) {
			this.target = target;
			return this;
		}
	}

	public static DynamicInvokJobBuilder builder() {
		return new DynamicInvokJobBuilder();
	}

	/**
	 * cron 表达式
	 */
	@Getter
	private String cronExpression;

	/**
	 * 任务是否有状态 ， 默认无状态
	 */
	@Getter
	private boolean isConcurrent = false;
	private transient JobDetail jobDetail;
	/**
	 * 任务分组
	 */
	@Getter
	private String jobGroup = DynamicSchedulerFactory.DEFAULT_GROUP;

	/**
	 * 必须唯一. Must unique, is identifier， 任务的唯一标识
	 */
	@Getter
	private String jobName;
	/**
	 * 任务调用的方法名
	 */
	@Getter
	private String methodName;
	/**
	 * 任务执行时调用哪个类的方法 包名+类名 , 不需要实现Job接口
	 */
	@Getter
	private String target;
	private transient TriggerKey triggerKey;

	private DynamicInvokJob(final String jobName, final String target, final String methodName,
			final String cronExpression, final String jobGroup, final boolean isConcurrent) {
		this.jobName = jobName;
		this.target = target;
		this.methodName = methodName;
		this.cronExpression = cronExpression;
		this.jobGroup = jobGroup;
		this.isConcurrent = isConcurrent;
	}

	/**
	 * Transfer data to job In job use context.getMergedJobDataMap().get(key)
	 * <br>
	 * 传参数给 执行的 job 在job中 通过 context.getMergedJobDataMap().get(key) 获取值
	 */
	public DynamicInvokJob addJobData(final String key, final Object value) {
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
	public DynamicInvokJob addJobDataMap(final Map<String, Object> map) {
		final JobDetail detail = getJobDetail();
		final JobDataMap jobDataMap = detail.getJobDataMap();
		jobDataMap.putAll(map);
		return this;
	}

	/**
	 * @return 获取CronTrigger触发器
	 */
	public CronTrigger cronTrigger() {
		// eg。
		// newTrigger().withIdentity("trigger1",
		// "group1").withSchedule(cronSchedule("0/20 * * * * ?")).build();
		final CronScheduleBuilder cronScheduleBuilder = cronSchedule(this.cronExpression);
		return newTrigger().withIdentity(getTriggerKey()).withSchedule(cronScheduleBuilder).build();
	}

	public JobDetail getJobDetail() {
		if (jobDetail == null) {
			if (isConcurrent) {
				jobDetail = newJob(QuartzJobFactoryExecution.class).withIdentity(this.jobName, this.jobGroup).build();
			} else {
				jobDetail = newJob(QuartzJobFactoryDisallowConcurrentExecution.class)
						.withIdentity(this.jobName, this.jobGroup).build();
			}
			addJobData("dynamicInvokJob", this);
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

}
