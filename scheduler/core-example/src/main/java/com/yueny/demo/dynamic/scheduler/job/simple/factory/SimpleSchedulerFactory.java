package com.yueny.demo.dynamic.scheduler.job.simple.factory;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class SimpleSchedulerFactory {
	private static Logger log = LoggerFactory.getLogger(SimpleSchedulerFactory.class);

	public static Scheduler getScheduler() throws SchedulerException {
		log.info("------- Initializing ----------------------");
		// First we must get a reference to a scheduler
		// 初始化一个Schedule工厂
		final SchedulerFactory sf = new StdSchedulerFactory("properties/quartz.properties");
		// final SchedulerFactory sf = new StdSchedulerFactory();
		// 通过schedule工厂类获得一个Scheduler类
		final Scheduler scheduler = sf.getScheduler();
		log.info("------- Initialization Complete -----------");

		return scheduler;
	}
}
