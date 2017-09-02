package com.yueny.demo.micros.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.yueny.demo.common.example.service.IDataPrecipitationService;

@Controller
public class WelcomeController extends BaseController {
	@Autowired
	private IDataPrecipitationService dataPrecipitationService;

	/*
	 * 提供路由信息，负责URL到Controller中的具体函数的映射。
	 */
	@GetMapping(value = { "/", "welcome" })
	public String home() {
		logger.info("主页 {} 被访问了~！", getEnv());

		addAttribute("userName", dataPrecipitationService.queryAll().size());
		return "welcome";
	}

}
