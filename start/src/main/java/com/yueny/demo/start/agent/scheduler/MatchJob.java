package com.yueny.demo.start.agent.scheduler;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yueny.demo.start.agent.service.MatchResultLogic;
import com.yueny.rapid.lang.util.UuidUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 匹配撮合任务 Created by chars on 2017/2/24 17:36
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年3月9日 下午4:47:40
 * @since 1.0.0
 */
@Component
@Slf4j
public class MatchJob {
	private static ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
	@Autowired
	private MatchResultLogic matchResultLogic;

	@PostConstruct
	public void execute() {
		executor.scheduleWithFixedDelay(() -> {
			go();
		}, 0, 1000, TimeUnit.MILLISECONDS);
	}

	private void go() {
		final String uid = UuidUtil.getSimpleUuid();

		log.info("MatchJob 任务处理批次:{}.", uid);
		matchResultLogic.match(uid);
	}

}
