package com.yueny.demo.dynamic.scheduler.job.core.factory;

import java.util.Date;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.yueny.demo.dynamic.scheduler.job.core.DynamicInvokJob;
import com.yueny.demo.dynamic.scheduler.job.core.DynamicJob;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * 管理动态Job的注册,删除等, 需要配置在xml配置文件中<br>
 * 当然,肯定需要schedulerFactory对象的引用<br>
 *
 * 实现动态的Job如下<br>
 * 1). 创建DynamicJob实例(jobName,cronExpression与target是必须的; target是Job类的一个实现)<br>
 * 2).调用DynamicSchedulerFactory的registerJob 注册一个Job或者 调用removeJob删除已添加的Job<br>
 *
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月28日 下午9:07:26
 *
 */
@Slf4j
public class DynamicSchedulerFactory implements InitializingBean {
	public static final String DEFAULT_GROUP = "DEFAULT";
	public static final String UNKNOWN_STATUS = "NA";

	@Setter
	private JobListener listener;
	@Setter
	private Scheduler scheduler;

	public DynamicSchedulerFactory() {
		// .
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(scheduler, "scheduler is null");
		log.info("Initial DynamicSchedulerFactory successful, scheduler instance: {}", scheduler);
	}

	/**
	 * 判断任务是否存在<br>
	 * Check the job is exist or not
	 */
	public boolean existJob(final DynamicJob job) throws SchedulerException {
		final TriggerKey triggerKey = job.getTriggerKey();

		return scheduler.checkExists(triggerKey);
	}

	/**
	 * 判断任务是否存在<br>
	 * Check the job is exist or not
	 */
	public boolean existJob(final String identifier) throws SchedulerException {
		return existJob(identifier, DEFAULT_GROUP);
	}

	/**
	 * 判断任务是否存在<br>
	 * Check the job is exist or not
	 */
	public boolean existJob(final String identifier, final String jobGroup) throws SchedulerException {
		final TriggerKey triggerKey = getTriggerKey(identifier, jobGroup);

		// Trigger trigger = scheduler.getTrigger(triggerKey);
		// return trigger != null;
		return scheduler.checkExists(triggerKey);
	}

	/**
	 * 检查调度是否启动
	 *
	 * @throws SchedulerException
	 */
	public boolean isStarted() throws SchedulerException {
		return scheduler.isStarted();
	}

	/**
	 * 暂停任务<br>
	 * Pause exist job
	 */
	public boolean pauseJob(final DynamicJob existJob) throws SchedulerException {
		final TriggerKey triggerKey = existJob.getTriggerKey();
		boolean result = false;
		if (existJob(existJob)) {
			scheduler.pauseTrigger(triggerKey);
			result = true;
			log.info("Pause exist DynamicJob {}, triggerKey [{}] successful", existJob, triggerKey);
		} else {
			log.info("Failed pause exist DynamicJob {}, because not fount triggerKey [{}]", existJob, triggerKey);
		}
		return result;
	}

	/**
	 * 注册一个job 1.调用 {@link #scheduler} 的 scheduleJob 加入
	 *
	 * @param invokJob
	 *            DynamicInvokJob
	 * @return True is register successful
	 * @throws org.quartz.SchedulerException
	 */
	public boolean registerJob(final DynamicInvokJob invokJob) throws SchedulerException {
		final TriggerKey triggerKey = invokJob.getTriggerKey();
		if (scheduler.checkExists(triggerKey)) {
			final Trigger trigger = scheduler.getTrigger(triggerKey);
			throw new SchedulerException(
					"Already exist trigger [" + trigger + "] by key [" + triggerKey + "] in Scheduler");
		}

		final CronTrigger cronTrigger = invokJob.cronTrigger();

		final JobDetail jobDetail = invokJob.getJobDetail();
		final Date ft = scheduler.scheduleJob(jobDetail, cronTrigger);

		log.info("Register DynamicInvokJob {} has been scheduled to run at [{}] and repeat based on expression:{}."
				+ cronTrigger.getCronExpression(), invokJob, ft);
		return true;
	}

	/**
	 * 注册一个job 1.检查是否有相同的 scheduler(根据 trigger key) ,若有则抛出异常 2.调用
	 * {@link #scheduler} 的 scheduleJob 加入
	 *
	 * @param job
	 *            DynamicJob
	 * @return True is register successful
	 * @throws org.quartz.SchedulerException
	 */
	public boolean registerJob(final DynamicJob job) throws SchedulerException {
		return registerJob(job, false);
	}

	/**
	 * 注册一个job 1.检查是否有相同的 scheduler(根据 trigger key) ,若有则抛出异常 2.调用
	 * {@link #scheduler} 的 scheduleJob 加入
	 *
	 * @param job
	 *            DynamicJob
	 * @param isRecove
	 *            如果任务存在，是否更新执行
	 * @return True is register successful
	 * @throws org.quartz.SchedulerException
	 */
	public boolean registerJob(final DynamicJob job, final boolean isRecove) throws SchedulerException {
		final TriggerKey triggerKey = job.getTriggerKey();
		if (existJob(job)) {
			final Trigger trigger = scheduler.getTrigger(triggerKey);

			if (!isRecove) {
				throw new SchedulerException(
						"Already exist trigger [" + trigger + "] by key [" + triggerKey + "] in Scheduler");
			}

			// TODO
			// // Trigger已存在，那么更新相应的定时设置
			// final CronScheduleBuilder cronScheduleBuilder =
			// cronSchedule(job.getCronExpression());
			//
			// // 按新的cronExpression表达式重新构建trigger
			// final CronTrigger trigger1 =
			// trigger.getTriggerBuilder().withIdentity(triggerKey)
			// .withSchedule(cronScheduleBuilder).build();
			//
			// // 按新的trigger重新设置job执行
			// scheduler.rescheduleJob(triggerKey, trigger1);
			// return true;
			throw new SchedulerException(
					"Already exist trigger [" + trigger + "] by key [" + triggerKey + "] in Scheduler");
		}

		final CronTrigger cronTrigger = job.cronTrigger();

		final JobDetail jobDetail = job.getJobDetail();
		final Date ft = scheduler.scheduleJob(jobDetail, cronTrigger);

		log.info("Register DynamicJob {} has been scheduled to run at [{}] and repeat based on expression:{}."
				+ cronTrigger.getCronExpression(), job, ft);
		return true;
	}

	/**
	 * 删除任务<br>
	 * Remove exists job<br>
	 * 删除掉已经存在的 job
	 *
	 * @param existJob
	 *            A DynamicJob which exists in Scheduler
	 * @return True is remove successful
	 * @throws org.quartz.SchedulerException
	 */
	public boolean removeJob(final DynamicJob existJob) throws SchedulerException {
		final TriggerKey triggerKey = existJob.getTriggerKey();
		boolean result = false;
		if (existJob(existJob)) {
			result = scheduler.unscheduleJob(triggerKey);
		}

		log.info("Remove DynamicJob {} result [{}]", existJob, result);
		return result;
	}

	/**
	 * 恢复任务<br>
	 * Resume exist job
	 */
	public boolean resumeJob(final DynamicJob existJob) throws SchedulerException {
		final TriggerKey triggerKey = existJob.getTriggerKey();
		boolean result = false;
		if (existJob(existJob)) {
			final CronTrigger newTrigger = existJob.cronTrigger();
			final Date date = scheduler.rescheduleJob(triggerKey, newTrigger);

			result = true;
			log.info("Resume exist DynamicJob {}, triggerKey [{}] on [{}] successful", existJob, triggerKey, date);
		} else {
			log.info("Failed resume exist DynamicJob {}, because not fount triggerKey [{}]", existJob, triggerKey);
		}
		return result;
	}

	/**
	 * 关闭调度信息
	 *
	 * @throws SchedulerException
	 */
	public void shutdown() throws SchedulerException {
		scheduler.shutdown();
	}

	/**
	 * 启动一个调度对象
	 *
	 * @throws SchedulerException
	 */
	public void start() throws SchedulerException {
		if (isStarted()) {
			return;
		}

		scheduler.start();
	}

	/**
	 * 启动服务
	 */
	public boolean startJob(final DynamicJob job) {
		boolean result = false;
		try {
			if (existJob(job)) {
				result = resumeJob(job);
				log.info("<{}> Resume  [{}] result: {}", job, result);
			} else {
				result = registerJob(job);
				log.debug("<{}> Register  [{}] result: {}", job, result);
			}
		} catch (final SchedulerException e) {
			log.error("<{}> startJob [" + job + "] failed", e);
		}
		return result;
	}

	private TriggerKey getTriggerKey(final String identifier) {
		return getTriggerKey(identifier, DEFAULT_GROUP);
	}

	private TriggerKey getTriggerKey(final String identifier, final String jobGroup) {
		return TriggerKey.triggerKey(identifier, jobGroup);
	}

}
