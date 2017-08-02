package com.yueny.demo.rocketmq.provider.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年2月16日 下午8:23:11
 *
 */
@Controller
public class DemoController {
	/**
	 *
	 */
	@RequestMapping(value = { "/", "welcome" }, method = RequestMethod.GET)
	@ResponseBody
	public String bar() {
		return "welcome";
	}

}
