package com.yueny.demo.micros.boot.scheduler.example.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年4月26日 下午3:20:37
 *
 */
@Slf4j
public class ScheduledExampleQuartzJob2 implements Job {
	@Override
	public void execute(final JobExecutionContext context) throws JobExecutionException {
		log.info("ScheduledExampleQuartzJob2: The time is now " + dateFormat().format(new Date()));
	}

	private SimpleDateFormat dateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
}
