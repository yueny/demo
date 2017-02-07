package com.yueny.demo.dynamic.scheduler.job.core.model;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月29日 上午12:39:45
 *
 */
@Deprecated
public abstract class JobParamManager {

	private static final String MONITORING_INSTANCE_JOB_NAME_PREFIX = "monitoring-instance-";

	public static String generateMonitoringInstanceJobName(final String key) {
		return MONITORING_INSTANCE_JOB_NAME_PREFIX + key;
	}

	protected JobParamManager() {
		// .
	}

}