package com.yueny.demo.job.scheduler.config.bind.model;

import com.yueny.demo.job.scheduler.config.bind.JopType;

/**
 * 任务对象
 * 
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年6月23日 下午4:39:01
 *
 */
public interface IJobBean {
	/**
	 * 任务执行表达式(作业启动时间的cron表达式)
	 */
	String getCron();

	/**
	 * 作业描述信息
	 */
	String getDescription();

	/**
	 * Class Name 作业名称
	 */
	String getName();

	/**
	 * 设置分片序列号和个性化参数对照表.
	 *
	 * <p>
	 * 分片序列号和参数用等号分隔, 多个键值对用逗号分隔. 类似map. 分片序列号从0开始, 不可大于或等于作业分片总数. 如:
	 * 0=a,1=b,2=c
	 * </p>
	 *
	 * @param shardingItemParameters
	 *            分片序列号和个性化参数对照表
	 *
	 * @return 作业配置构建器
	 */
	String getShardingItemParameters();

	/**
	 * 作业分片总数，默认1片
	 */
	int getShardingTotalCount();

	/**
	 * JopType
	 */
	JopType getType();

	/**
	 * 作业是否启动时禁止
	 */
	boolean isDisabled();

	/**
	 * 是否开启失效转移
	 */
	boolean isFailover();

	/**
	 * 监控作业执行时状态
	 */
	boolean isMonitorExecution();

	/**
	 * 本地配置是否可覆盖注册中心配置
	 */
	boolean isOverwrite();

}
