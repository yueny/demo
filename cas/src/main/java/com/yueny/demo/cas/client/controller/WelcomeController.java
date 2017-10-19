package com.yueny.demo.cas.client.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yueny.demo.annotation.AuthControl;
import com.yueny.rapid.data.resp.pojo.response.NormalResponse;

@Controller
@Description("欢迎页")
public class WelcomeController extends BaseController {

	/**
	 * 默认页<br/>
	 *
	 * @RequestMapping("/") 和 @RequestMapping 是有区别的<br/>
	 * 如果不写参数，则为全局默认页，假如输入404页面，也会自动访问到这个页面。<br/>
	 * 如果加了参数“/”，则只认为是根页面。<br/>
	 */
	@GetMapping(value = "/")
	@AuthControl(desc = "主页")
	public String home() {
		logger.info("主页  被访问了~！");

		addAttribute("title", "a");
		addAttribute("userName", "yueny09");
		return "welcome";
	}

	@RequestMapping("/service/{key}") // service/*/a/b.json
	// @AuthControl(desc = "模拟菜单")
	@ResponseBody
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
