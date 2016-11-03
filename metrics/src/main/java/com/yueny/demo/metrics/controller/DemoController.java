package com.yueny.demo.metrics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codahale.metrics.annotation.Counted;
import com.yueny.demo.metrics.service.IDemoService;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年2月16日 下午8:23:11
 *
 */
@Controller
public class DemoController {
	@Autowired
	private IDemoService demoService;

	/**
	 *
	 */
	@RequestMapping(value = "/demo/bar", method = RequestMethod.GET)
	@ResponseBody
	@Counted(name = "controllerBarCounted", monotonic = true)
	public String bar() {
		return demoService.bar();
	}

}
