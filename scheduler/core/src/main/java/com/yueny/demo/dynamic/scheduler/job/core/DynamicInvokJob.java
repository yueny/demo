package com.yueny.demo.dynamic.scheduler.job.core;

import static org.quartz.JobBuilder.newJob;

import java.io.Serializable;

import org.quartz.JobDetail;

import com.yueny.demo.dynamic.scheduler.job.core.invok.QuartzJobFactoryDisallowConcurrentExecution;
import com.yueny.demo.dynamic.scheduler.job.core.invok.QuartzJobFactoryExecution;

import lombok.Getter;
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
// @NoArgsConstructor
public class DynamicInvokJob extends BaseJob implements Serializable {
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

	/**
	 *
	 */
	private static final long serialVersionUID = -5410735434693050919L;

	public static DynamicInvokJobBuilder builder() {
		return new DynamicInvokJobBuilder();
	}

	/**
	 * cron 表达式
	 */
	@Getter
	private final String cronExpression;

	/**
	 * 任务是否有状态 ， 默认无状态
	 */
	@Getter
	private boolean isConcurrent = false;
	private transient JobDetail jobDetail;

	/**
	 * 任务调用的方法名
	 */
	@Getter
	private final String methodName;
	/**
	 * 任务执行时调用哪个类的方法 包名+类名 , 不需要实现Job接口
	 */
	@Getter
	private final String target;

	private DynamicInvokJob(final String jobName, final String target, final String methodName,
			final String cronExpression, final String jobGroup, final boolean isConcurrent) {
		super(jobName, jobGroup);
		this.target = target;
		this.methodName = methodName;
		this.cronExpression = cronExpression;
		this.isConcurrent = isConcurrent;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.yueny.demo.dynamic.scheduler.job.core.api.IJob#getJobDetail()
	 */
	@Override
	public JobDetail getJobDetail() {
		if (jobDetail == null) {
			if (!isConcurrent) {
				jobDetail = newJob(QuartzJobFactoryExecution.class).withIdentity(getJobName(), getJobGroup()).build();
			} else {
				jobDetail = newJob(QuartzJobFactoryDisallowConcurrentExecution.class)
						.withIdentity(getJobName(), getJobGroup()).build();
			}
			addJobData("dynamicInvokJob", this);
		}

		return jobDetail;
	}

}
