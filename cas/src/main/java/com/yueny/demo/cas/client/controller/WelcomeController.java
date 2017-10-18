package com.yueny.demo.cas.client.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yueny.demo.annotation.AuthControl;
import com.yueny.rapid.data.resp.pojo.response.NormalResponse;

@Controller
@Description("欢迎页")
public class WelcomeController extends BaseController {

	/*
	 * 提供路由信息，负责URL到Controller中的具体函数的映射。
	 */
	@GetMapping(value = "/")
	@AuthControl(desc = "主页")
	public String home() {
		logger.info("主页  被访问了~！");

		addAttribute("title", "a");
		return "welcome";
	}

	@RequestMapping("/service/{key}") // service/*/a/b.json
	// @AuthControl(desc = "模拟菜单")
	public NormalResponse<String> key(@PathVariable final String key) {
		logger.info(" 被访问了~！", key);

		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (final InterruptedException e) {
			System.out.println("sleep Interrupted!");
		}

		final NormalResponse<String> reps = new NormalResponse<>();
		reps.setData(key + "被访问了~！");
		return reps;
	}

}
