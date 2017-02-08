package com.yueny.demo.dynamic.scheduler.job.core;

import static org.quartz.JobBuilder.newJob;

import org.quartz.Job;
import org.quartz.JobDetail;

import lombok.Getter;
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
public class DynamicJob extends BaseJob {
	@ToString
	public static class DynamicJobBuilder {
		private String cronExpression;
		private String jobGroup;
		private String jobName;
		private Class<? extends Job> target;

		private DynamicJobBuilder() {
		}

		public DynamicJob build() {
			return new DynamicJob(jobName, target, cronExpression, jobGroup);
		}

		public DynamicJobBuilder cronExpression(final String cronExpression) {
			this.cronExpression = cronExpression;
			return this;
		}

		public DynamicJobBuilder jobGroup(final String jobGroup) {
			this.jobGroup = jobGroup;
			return this;
		}

		public DynamicJobBuilder jobName(final String jobName) {
			this.jobName = jobName;
			return this;
		}

		public DynamicJobBuilder target(final Class<? extends Job> target) {
			this.target = target;
			return this;
		}
	}

	public static DynamicJobBuilder builder() {
		return new DynamicJobBuilder();
	}

	/**
	 * cron 表达式
	 */
	@Getter
	private final String cronExpression;

	private transient JobDetail jobDetail;

	/**
	 * 要执行类, 实现Job接口
	 */
	@Getter
	private final Class<? extends Job> target;

	private DynamicJob(final String jobName, final Class<? extends Job> target, final String cronExpression,
			final String jobGroup) {
		super(jobName, jobGroup);
		this.target = target;
		this.cronExpression = cronExpression;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.yueny.demo.dynamic.scheduler.job.core.api.IJob#getJobDetail()
	 */
	@Override
	public JobDetail getJobDetail() {
		if (jobDetail == null) {
			// eg.
			// newJob(SimpleDemoQuartzJob.class).withIdentity("job1",
			// "group1").build();
			jobDetail = newJob(target).withIdentity(getJobName(), getJobGroup()).build();
		}
		return jobDetail;
	}

}
