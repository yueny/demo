package com.yueny.demo.dynamic.scheduler.job.dynamic;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

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
	@Setter
	@Getter
	private Scheduler scheduler;

	public DynamicSchedulerFactory() {
		// .
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(scheduler, "scheduler is null");
		log.info(
				"Initial DynamicSchedulerFactory successful, scheduler instance: {}",
				scheduler);
	}

	/**
	 * Check the job is exist or not
	 * */
	public boolean existJob(final DynamicJob job) throws SchedulerException {
		final TriggerKey triggerKey = job.triggerKey();
		return scheduler.checkExists(triggerKey);
	}

	/**
	 * Pause exist job
	 * */
	public boolean pauseJob(final DynamicJob existJob)
			throws SchedulerException {
		final TriggerKey triggerKey = existJob.triggerKey();
		boolean result = false;
		if (existJob(existJob)) {
			scheduler.pauseTrigger(triggerKey);
			result = true;
			log.info("Pause exist DynamicJob {}, triggerKey [{}] successful",
					existJob, triggerKey);
		} else {
			log.info(
					"Failed pause exist DynamicJob {}, because not fount triggerKey [{}]",
					existJob, triggerKey);
		}
		return result;
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
		final TriggerKey triggerKey = job.triggerKey();
		if (existJob(job)) {
			final Trigger trigger = scheduler.getTrigger(triggerKey);
			throw new SchedulerException("Already exist trigger [" + trigger
					+ "] by key [" + triggerKey + "] in Scheduler");
		}

		final CronTrigger cronTrigger = job.cronTrigger();

		final JobDetail jobDetail = job.jobDetail();
		final Date date = scheduler.scheduleJob(jobDetail, cronTrigger);

		log.info("Register DynamicJob {} on [{}]", job, date);
		return true;
	}

	/**
	 * Remove exists job<br>
	 * 删除掉已经存在的 job
	 *
	 * @param existJob
	 *            A DynamicJob which exists in Scheduler
	 * @return True is remove successful
	 * @throws org.quartz.SchedulerException
	 */
	public boolean removeJob(final DynamicJob existJob)
			throws SchedulerException {
		final TriggerKey triggerKey = existJob.triggerKey();
		boolean result = false;
		if (existJob(existJob)) {
			result = scheduler.unscheduleJob(triggerKey);
		}

		log.info("Remove DynamicJob {} result [{}]", existJob, result);
		return result;
	}

	/**
	 * Resume exist job
	 * */
	public boolean resumeJob(final DynamicJob existJob)
			throws SchedulerException {
		final TriggerKey triggerKey = existJob.triggerKey();
		boolean result = false;
		if (existJob(existJob)) {
			final CronTrigger newTrigger = existJob.cronTrigger();
			final Date date = scheduler.rescheduleJob(triggerKey, newTrigger);

			result = true;
			log.info(
					"Resume exist DynamicJob {}, triggerKey [{}] on [{}] successful",
					existJob, triggerKey, date);
		} else {
			log.info(
					"Failed resume exist DynamicJob {}, because not fount triggerKey [{}]",
					existJob, triggerKey);
		}
		return result;
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

}
