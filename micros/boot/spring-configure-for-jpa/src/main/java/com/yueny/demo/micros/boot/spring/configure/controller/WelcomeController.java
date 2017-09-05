package com.yueny.demo.micros.boot.spring.configure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yueny.demo.micros.boot.spring.configure.core.context.WelcomeProperties;
import com.yueny.demo.micros.boot.spring.configure.service.IModifyDemoService;

@Controller
@Description("欢迎页")
public class WelcomeController extends BaseController {
	@Autowired
	private IModifyDemoService modifyDemoService;
	@Autowired
	private WelcomeProperties welcomeProperties;

	/*
	 * 提供路由信息，负责URL到Controller中的具体函数的映射。
	 */
	@GetMapping(value = "/")
	public String home() {
		logger.info("主页 {} 被访问了~！", getEnv());

		addAttribute("title", this.welcomeProperties.getName());
		addAttribute("userName", modifyDemoService.queryByType("N").size());
		return "welcome";
	}

	@RequestMapping(value = "/welcomeyou/{id}")
	public String homeFor(@PathVariable final Long id) {
		logger.info("主页 {} 被访问了~！", getEnv());

		addAttribute("title", this.welcomeProperties.getName());
		addAttribute("userName", modifyDemoService.queryById(id));
		return "welcome";
	}

	@RequestMapping(value = "/welcome/{name}")
	public String homeFor(@PathVariable final String name) {
		logger.info("主页 {} 被访问了~！", getEnv());

		addAttribute("title", this.welcomeProperties.getName());
		addAttribute("userName", modifyDemoService.queryByName(name));
		return "welcome";
	}
}
