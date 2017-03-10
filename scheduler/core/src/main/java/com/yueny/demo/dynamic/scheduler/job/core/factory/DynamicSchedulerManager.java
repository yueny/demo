package com.yueny.demo.dynamic.scheduler.job.core.factory;

import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.impl.matchers.EverythingMatcher.allJobs;

import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.yueny.demo.dynamic.scheduler.job.core.DynamicInvokJob;
import com.yueny.demo.dynamic.scheduler.job.core.DynamicJob;
import com.yueny.demo.dynamic.scheduler.job.core.api.IJob;
import com.yueny.demo.dynamic.scheduler.job.core.enums.JobStatusType;
import com.yueny.demo.dynamic.scheduler.job.core.listener.QuartzJobListener;
import com.yueny.demo.dynamic.scheduler.job.core.model.TriggerInfo;
import com.yueny.rapid.lang.date.DateUtil;

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
public class DynamicSchedulerManager implements InitializingBean {
	public static final String DEFAULT_GROUP = "DEFAULT";
	public static final String UNKNOWN = "N/A";

	private QuartzJobListener listener;
	@Setter
	private Scheduler scheduler;

	public DynamicSchedulerManager() {
		// .
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(scheduler, "scheduler is null");
		if (listener == null) {
			listener = new QuartzJobListener();
		}

		log.info("Initial DynamicSchedulerFactory successful, scheduler instance: {}", scheduler);
	}

	/**
	 * 判断任务是否存在<br>
	 * Check the job is exist or not
	 */
	public boolean existJob(final IJob job) throws SchedulerException {
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
	 * 根据jobName和jobGroup获取Trigger
	 *
	 * @param identifier
	 *            jobName
	 * @param jobGroup
	 *            jobGroup
	 */
	public Trigger getTrigger(final String identifier, final String jobGroup) {
		try {
			return scheduler.getTrigger(getTriggerKey(identifier, jobGroup));
		} catch (final SchedulerException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取任务状态
	 *
	 * @param trigger
	 *            Trigger
	 * @return 任务状态
	 */
	public TriggerInfo getTriggerInfo(final String identifier, final String jobGroup) throws SchedulerException {
		final TriggerInfo info = new TriggerInfo();

		final TriggerKey triggerKey = getTriggerKey(identifier, jobGroup);
		JobStatusType taskStatus = JobStatusType.EXPIRED;
		if (triggerKey != null) {
			info.setTriggerKey(triggerKey);
		}

		// 获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
		final Trigger trigger = scheduler.getTrigger(triggerKey);
		if (trigger != null) {
			info.setTrigger(trigger);
			taskStatus = JobStatusType.valueOf(scheduler.getTriggerState(triggerKey).name());
		}

		info.setTaskStatus(taskStatus);
		return info;
	}

	/**
	 * 根据jobName和jobGroup获取TriggerKey
	 *
	 * @param identifier
	 *            jobName
	 * @param jobGroup
	 *            jobGroup
	 */
	public TriggerKey getTriggerKey(final String identifier, final String jobGroup) {
		return TriggerKey.triggerKey(identifier, jobGroup);
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
	 * 注册一个job 1.检查是否有相同的 scheduler(根据 trigger key) ,若有则抛出异常 2.调用
	 * {@link #scheduler} 的 scheduleJob 加入
	 *
	 * @param invokJob
	 *            DynamicInvokJob
	 * @param isRecove
	 *            如果任务存在，是否更新执行
	 * @return True is register successful
	 * @throws org.quartz.SchedulerException
	 */
	private boolean registerIJob(final IJob ijob, final boolean isRecove) {
		try {
			if (existJob(ijob)) {
				final TriggerKey triggerKey = ijob.getTriggerKey();
				// 获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
				Trigger trigger = scheduler.getTrigger(triggerKey);
				if (!isRecove) {
					throw new SchedulerException(
							"Already exist trigger [" + trigger + "] by key [" + triggerKey + "] in Scheduler");
				}

				if (trigger instanceof CronTrigger) {
					final CronTrigger cronTrigger = (CronTrigger) trigger;
					// Trigger已存在，更新任务（时间表达式） 表达式调度构建器
					final CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder
							.cronSchedule(ijob.getCronExpression());

					// 按新的cronExpression表达式重新构建trigger
					// TODO 更新 JobData
					trigger = cronTrigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(cronScheduleBuilder)
							.build();
				} else {
					final Date startTime = DateUtil.parse(ijob.getCronExpression());
					trigger = newTrigger().withIdentity(triggerKey).startAt(startTime).build();
				}

				// 按新的trigger重新设置job执行
				scheduler.rescheduleJob(triggerKey, trigger);
				return true;
			}

			final Trigger cronTrigger = ijob.getTrigger();
			final JobDetail jobDetail = ijob.getJobDetail();

			// TODO
			ijob.addJobData("listenerxxx", "listener");
			scheduler.getListenerManager().addJobListener(listener, allJobs());

			final Date ft = scheduler.scheduleJob(jobDetail, cronTrigger);
			log.info("Register IJob {} has been scheduled to run at [{}] and repeat based on expression:{}."
					+ ijob.getCronExpression(), ijob, ft);
			return true;
		} catch (final SchedulerException e) {
			e.printStackTrace();
			log.error("任务添加异常", e);
		}
		return false;
	}

	/**
	 * 注册一个job 1.检查是否有相同的 scheduler(根据 trigger key) ,若有则抛出异常 2.调用
	 * {@link #scheduler} 的 scheduleJob 加入
	 *
	 * @param invokJob
	 *            DynamicInvokJob
	 * @return True is register successful
	 * @throws org.quartz.SchedulerException
	 */
	public boolean registerJob(final DynamicInvokJob invokJob) {
		return registerJob(invokJob, false);
	}

	/**
	 * 注册一个job 1.检查是否有相同的 scheduler(根据 trigger key) ,若有则抛出异常 2.调用
	 * {@link #scheduler} 的 scheduleJob 加入
	 *
	 * @param invokJob
	 *            DynamicInvokJob
	 * @param isRecove
	 *            如果任务存在，是否更新执行
	 * @return True is register successful
	 * @throws org.quartz.SchedulerException
	 */
	public boolean registerJob(final DynamicInvokJob invokJob, final boolean isRecove) {
		return registerIJob(invokJob, isRecove);
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
	public boolean registerJob(final DynamicJob job) {
		return registerJob(job, false);
	}

	/**
	 * 注册一个job 1.检查是否有相同的 scheduler(根据 trigger key) ,若有则抛出异常 2.调用
	 * {@link #scheduler} 的 scheduleJob 加入
	 *
	 * @param scheduleJob
	 *            DynamicJob
	 * @param isRecove
	 *            如果任务存在，是否更新执行
	 * @return True is register successful
	 * @throws org.quartz.SchedulerException
	 */
	public boolean registerJob(final DynamicJob scheduleJob, final boolean isRecove) {
		return registerIJob(scheduleJob, isRecove);
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
			final Trigger newTrigger = existJob.getTrigger();
			final Date date = scheduler.rescheduleJob(triggerKey, newTrigger);

			result = true;
			log.info("Resume exist DynamicJob {}, triggerKey [{}] on [{}] successful", existJob, triggerKey, date);
		} else {
			log.info("Failed resume exist DynamicJob {}, because not fount triggerKey [{}]", existJob, triggerKey);
		}
		return result;
	}

	/**
	 * @param listener
	 *            the listener to set
	 */
	public void setListener(QuartzJobListener listener) {
		this.listener = listener;
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

}
