package com.yueny.demo.job.scheduler.job.tb.helper;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.taobao.pamirs.schedule.strategy.TBScheduleManagerFactory;

/**
 * TBSchedule初始化器
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年11月18日 上午8:54:21
 *
 */
public class TBScheduleInitializer implements ApplicationContextAware {
	private static final Logger logger = LoggerFactory.getLogger(TBScheduleInitializer.class);

	/**
	 * 密码，任意指定，调度控制台配置时对应
	 */
	private final String consolePassword;
	/**
	 * 账户，任意指定，调度控制台配置时对应
	 */
	private final String consoleUserName;
	/**
	 */
	private final String isCheckParentPath;
	/**
	 * 定时任务根目录，任意指定，调度控制台配置时对应
	 */
	private final String rootPath;
	/**
	 * 超时配置
	 */
	private final String scheduleTimeout;
	/**
	 * 注册中心地址, eg> 172.26.50.86:2181
	 */
	private final String zkConnectString;

	public TBScheduleInitializer(final String zkConnectString, final String consoleUserName,
			final String consolePassword, final String rootPath, final String scheduleTimeout,
			final String isCheckParentPath) {
		this.zkConnectString = zkConnectString;
		this.consoleUserName = consoleUserName;
		this.consolePassword = consolePassword;
		this.rootPath = rootPath;
		this.scheduleTimeout = scheduleTimeout;
		this.isCheckParentPath = isCheckParentPath;
	}

	@Override
	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		try {
			// 初始化调度工厂
			final TBScheduleManagerFactory scheduleManagerFactory = new TBScheduleManagerFactory();
			final Properties p = new Properties();

			p.put("zkConnectString", zkConnectString);
			p.put("userName", consoleUserName);
			p.put("password", consolePassword);
			p.put("rootPath", rootPath);
			p.put("zkSessionTimeout", scheduleTimeout);
			p.put("isCheckParentPath", isCheckParentPath);

			scheduleManagerFactory.setApplicationContext(applicationContext);
			scheduleManagerFactory.init(p);
		} catch (final Exception e) {
			logger.error("初始化TBSchedule错误", e);
			throw new FatalBeanException("初始化TBSchedule错误", e);
		}
	}

}
