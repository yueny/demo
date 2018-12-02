package com.yueny.demo.micros.cloud.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yueny.demo.micros.cloud.service.IMailService;
import com.yueny.rapid.data.resp.pojo.response.NormalResponse;

/*
 */
@RestController
public class OpertController extends BaseController {
	@Autowired
	private IMailService mailService;

	/**
	 * 注册行为模拟
	 */
	@RequestMapping("/register")
	public NormalResponse<Boolean> register(final String username) {
		logger.info("register~！", username);

		try {
			TimeUnit.MILLISECONDS.sleep(200);
		} catch (final InterruptedException e) {
			System.out.println("sleep Interrupted!");
		}

		final Map<String, String> maps = new HashMap<>();
		maps.put("id", "666");
		final boolean rs = mailService.sendTemplateMail("yuany@aliyun.com", "REGISTER", "email/emailTemplate", maps);

		final NormalResponse<Boolean> reps = new NormalResponse<>();
		reps.setData(rs);
		return reps;
	}

}
