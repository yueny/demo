package com.yueny.demo.metrics.service.impl;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;
import com.yueny.demo.metrics.bo.ModifyDemoBo;
import com.yueny.demo.metrics.service.IDataPrecipitationService;
import com.yueny.demo.metrics.service.IDemoService;
import com.yueny.rapid.lang.json.JsonUtil;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年11月3日 下午1:48:56
 *
 */
@Service
public class DemoServiceImpl implements IDemoService {
	@Autowired
	private IDataPrecipitationService dataPrecipitationService;

	private final Random rn = new Random();

	@Override
	@Metered(name = "barMetered")
	@Timed(name = "barTimed")
	@Counted(name = "barCounted", monotonic = true) // 不累计当前并发数, 累计总数
	public String bar() {
		waitSeconds();

		final Integer pk = rn.nextInt(35);
		final ModifyDemoBo demoBo = dataPrecipitationService.findById(pk.longValue());

		if (demoBo == null) {
			return "I'm null";
		}
		return JsonUtil.toJson(demoBo);
	}

	private void waitSeconds() {
		try {
			TimeUnit.MILLISECONDS.sleep(600);
		} catch (final InterruptedException e) {
		}
	}
}
