package com.yueny.demo.dynamic.scheduler.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;

import com.yueny.demo.dynamic.scheduler.dao.QuartzDao;
import com.yueny.demo.dynamic.scheduler.entry.JobInfo;

/**
 * 调度器，用于作业的添加、删除、启动
 *
 * @ClassName: ScheduleService
 * @Description: TODO
 * @author: zhouhua
 * @date: 2016年3月11日 下午3:32:13
 */
@Deprecated
public class ScheduleService {

	@Autowired
	private QuartzDao quartzDao;

	@Autowired
	private Scheduler scheduler;// 作业调度器

	/**
	 * 暂停作业
	 *
	 * @Title: pauseTrigger
	 * @Description: TODO
	 * @param triggerName
	 * @param group
	 * @return: void
	 * @throws Exception
	 */
	public void pauseTrigger(final String triggerName, final String group) throws Exception {

		final TriggerKey triggerKey = geTriggerKey(triggerName, group);

		try {
			scheduler.pauseTrigger(triggerKey);
		} catch (final SchedulerException e) {
			throw new Exception("暂停作业是发生异常", e);
		}
	}

	/**
	 * 查询作业信息
	 *
	 * @Title: queryInfo
	 * @Description: TODO
	 * @param params
	 * @return
	 * @return: List<JobInfo>
	 */
	public List<JobInfo> queryInfo(final Map<String, String> params) {

		return quartzDao.getList(params);

	}

	/**
	 * 删除触发器
	 *
	 * @Title: removeTrigdger
	 * @Description: TODO
	 * @param triggerName
	 * @param group
	 * @return
	 * @return: boolean
	 * @throws Exception
	 */
	public boolean removeTrigdger(final String triggerName, final String group) throws Exception {

		final TriggerKey triggerKey = geTriggerKey(triggerName, group);
		try {
			scheduler.pauseTrigger(triggerKey);// 停止触发器
			return scheduler.unscheduleJob(triggerKey);// 移除触发器
		} catch (final SchedulerException e) {
			throw new Exception("删除作业时发生异常", e);
		}
	}

	/**
	 * 更新触发器的时间规则
	 *
	 * @Title: rescheduleJob
	 * @Description: TODO
	 * @param triggerName
	 *            触发器名称
	 * @param group
	 *            触发器分组
	 * @param cronExpression
	 *            时间规则
	 * @throws ParseException
	 *             解析异常
	 * @throws SchedulerException
	 *             调度器异常
	 * @return: void
	 */
	public void rescheduleJob(final String triggerName, final String group, final String cronExpression)
			throws ParseException, SchedulerException {

		final TriggerKey triggerKey = geTriggerKey(triggerName, group);
		try {
			final Trigger newTrigger = getTrigger(triggerName, group, cronExpression);
			scheduler.rescheduleJob(triggerKey, newTrigger);
		} catch (final ParseException e) {
			throw e;
		} catch (final SchedulerException e) {
			throw e;
		}

	}

	/**
	 * 重启作业
	 *
	 * @Title: resumeTrigger
	 * @Description: TODO
	 * @param triggerName
	 * @param group
	 * @throws Exception
	 * @return: void
	 */
	public void resumeTrigger(final String triggerName, final String group) throws Exception {

		final TriggerKey triggerKey = geTriggerKey(triggerName, group);

		try {
			scheduler.resumeTrigger(triggerKey);
		} catch (final SchedulerException e) {
			throw new Exception("重启作业时发生异常", e);
		}
	}

	/**
	 * 新增作业
	 *
	 * @Title: scheduleJob
	 * @Description: TODO
	 * @param jobName
	 *            作业名称
	 * @param group
	 *            作业和触发器所在分组
	 * @param job_class_name
	 *            作业的类名称，必须是全限定类名 如：com.acca.xxx.service（不能是接口）
	 * @param triggerName
	 *            触发器名称
	 * @param cronExpression
	 *            时间规则
	 * @param description
	 *            描述
	 * @return: void
	 * @throws ParseException
	 * @throws ClassNotFoundException
	 * @throws SchedulerException
	 */
	public void scheduleJob(final String jobName, final String group, final String job_class_name,
			final String triggerName, final String cronExpression, final String description)
			throws ParseException, ClassNotFoundException, SchedulerException {

		try {
			final JobDetail jobDetail = getJobDetail(jobName, group, job_class_name, description);
			final Trigger trigger = getTrigger(triggerName, group, cronExpression, description);
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (final ClassNotFoundException e) {
			throw e;
		} catch (final ParseException e) {
			throw e;
		} catch (final SchedulerException e) {
			throw e;
		}
	}

	/**
	 * 立即执行作业，不考虑时间规则
	 *
	 * @Title: triggerJob
	 * @Description: TODO
	 * @param jobName
	 *            作业名称
	 * @param group
	 *            作业分组
	 * @throws SchedulerException
	 *             调度器异常
	 * @return: void
	 */
	public void triggerJob(final String jobName, final String group) throws SchedulerException {

		final JobKey jobKey = new JobKey(jobName, group);

		try {
			scheduler.triggerJob(jobKey);
		} catch (final SchedulerException e) {
			throw new SchedulerException("立即执行作业时发生异常", e);
		}
	}

	/**
	 * 创建Jobdetail类
	 *
	 * @Title: getJobDetail
	 * @Description: TODO
	 * @param jobName
	 *            作业名称
	 * @param group
	 *            作业所在组
	 * @param job_class_name
	 *            作业的权限定类名
	 * @param description
	 * @return
	 * @throws ClassNotFoundException
	 * @return: JobDetail
	 */
	private JobDetail getJobDetail(final String jobName, final String group, final String job_class_name,
			final String description) throws ClassNotFoundException {

		try {
			final JobDetailImpl detail = new JobDetailImpl(jobName, group,
					(Class<? extends Job>) Class.forName(job_class_name));
			detail.setDescription(description);
			return detail;
		} catch (final ClassNotFoundException e) {
			throw new ClassNotFoundException(job_class_name + "不存在或者书写不正确", e);
		}
	}

	/**
	 * 获取触发器的key
	 *
	 * @Title: geTriggerKey
	 * @Description: TODO
	 * @param triggerName
	 * @param group
	 * @return
	 * @return: TriggerKey
	 */
	private TriggerKey geTriggerKey(final String triggerName, final String group) {

		final TriggerKey triggerKey = new TriggerKey(triggerName, group);

		return triggerKey;
	}

	/**
	 * 获取出发器
	 *
	 * @Title: getTrigger
	 * @Description: TODO
	 * @param triggerName
	 *            触发器名称
	 * @param group
	 *            触发器分组
	 * @param cronExpression
	 *            触发器时间规则
	 * @return
	 * @throws ParseException
	 *             解析异常
	 * @return: Trigger
	 */
	private Trigger getTrigger(final String triggerName, final String group, final String cronExpression)
			throws ParseException {

		final CronTriggerImpl triggerImpl = new CronTriggerImpl(triggerName, group);
		try {
			triggerImpl.setCronExpression(cronExpression);
		} catch (final ParseException e) {
			throw e;
		}
		return triggerImpl;
	}

	/**
	 * 创建触发器
	 *
	 * @Title: getTrigger
	 * @Description: TODO
	 * @param triggerName
	 *            触发器名称
	 * @param group
	 *            触发器所在组
	 * @param cronExpression
	 *            触发器的时间规则
	 * @return
	 * @throws ParseException
	 *             解析时间规则异常
	 * @return: Trigger
	 */
	private Trigger getTrigger(final String triggerName, final String group, final String cronExpression,
			final String description) throws ParseException {

		final CronTriggerImpl triggerImpl = new CronTriggerImpl(triggerName, group);
		triggerImpl.setDescription(description);
		try {
			triggerImpl.setCronExpression(cronExpression);
		} catch (final ParseException e) {
			throw e;
		}
		return triggerImpl;
	}

}
