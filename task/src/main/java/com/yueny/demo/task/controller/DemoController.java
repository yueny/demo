package com.yueny.demo.task.controller;

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
@RequestMapping("/task/demo")
public class DemoController {
	/**
	 *
	 */
	@RequestMapping(value = "/run", method = RequestMethod.GET)
	@ResponseBody
	public String run() {
		return "DemoController 来了...";
	}

}
