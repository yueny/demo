package com.yueny.demo.metrics.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;
import com.yueny.demo.metrics.service.IDemoService;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年11月3日 下午1:48:56
 *
 */
@Service
public class DemoServiceImpl implements IDemoService {

	@Override
	@Metered(name = "barMetered")
	@Timed(name = "barTimed")
	@Counted(name = "barCounted", monotonic = true) // 不累计当前并发数, 累计总数
	public String bar() {
		waitSeconds();

		return "DemoController 来了...";
	}

	private void waitSeconds() {
		try {
			TimeUnit.MILLISECONDS.sleep(1 * 1000);
		} catch (final InterruptedException e) {
		}
	}
}
