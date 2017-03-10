/**
 *
 */
package com.yueny.demo.dynamic.scheduler.job.core.api;

import java.util.Map;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

import com.yueny.demo.dynamic.scheduler.job.core.enums.JobDataKeyType;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年2月8日 上午11:42:40
 * @since
 */
public interface IJob {
	/**
	 * Transfer data to job In job use context.getMergedJobDataMap().get(key)
	 * <br>
	 * 传参数给 执行的 job 在job中 通过 context.getMergedJobDataMap().get(key) 获取值
	 */
	IJob addJobData(final JobDataKeyType key, final Object value);

	/**
	 * Transfer data to job In job use context.getMergedJobDataMap().get(key)
	 * <br>
	 * 传参数给 执行的 job 在job中 通过 context.getMergedJobDataMap().get(key) 获取值
	 */
	IJob addJobData(final String key, final Object value);

	/**
	 * Transfer data to job In job use context.getMergedJobDataMap().get(key)
	 * <br>
	 * 传参数给 执行的 job 在job中 通过 context.getMergedJobDataMap().get(key) 获取值
	 */
	IJob addJobDataMap(final Map<String, Object> map);

	/**
	 * 任务执行的cron 表达式/执行时间
	 */
	String getCronExpression();

	JobDetail getJobDetail();

	/**
	 * @return 获取Trigger触发器, 可能是CronTrigger/SimpleTrigger/...
	 */
	Trigger getTrigger();

	/**
	 * @return 获取TriggerKey
	 */
	TriggerKey getTriggerKey();
}
