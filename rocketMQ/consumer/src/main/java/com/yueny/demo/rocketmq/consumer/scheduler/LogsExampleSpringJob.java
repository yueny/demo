package com.yueny.demo.rocketmq.consumer.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年4月18日 下午6:26:57
 *
 */
@Slf4j
@Service
public class LogsExampleSpringJob {
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Value("${redis.client.password}")
	private String redisClientPassword;
	@Value("${redis.maxIdle}")
	private Integer redisMaxIdle;
	@Value("${redis.sentinel.2.host}")
	private String redisSentinel2Host;

	/**
	 * 默认的执行方式是串行执行
	 *
	 * @Scheduled 注解用于标注这个方法是一个定时任务的方法，有多种配置可选。<br>
	 *            cron支持cron表达式，指定任务在特定时间执行；<br>
	 *            fixedRate以特定频率执行任务， 单位 毫秒milliseconds；<br>
	 *            fixedRateString以string的形式配置执行频率。
	 */
	@Scheduled(fixedRate = 60000)
	public void doSomething() {
		// 间隔60 * 1000 毫秒(1分钟),执行任务
		try {
			final Thread thread = Thread.currentThread();
			log.info("定时任务{}/{}，The time is now ：{}.{}/{}/{}。", thread.getId(), thread.getName(),
					dateFormat.format(new Date()), redisClientPassword, redisSentinel2Host, redisMaxIdle);
		} catch (final Exception e) {
			log.error("【LogsExampleSpringJob任务】 超过超时，下次继续.");
		}
	}

}
