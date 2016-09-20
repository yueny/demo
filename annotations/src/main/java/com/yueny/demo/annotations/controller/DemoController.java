package com.yueny.demo.annotations.controller;

import org.springframework.stereotype.Controller;

import com.yueny.demo.annotations.annotation.DemoAnnotation;
import com.yueny.demo.annotations.annotation.DemoMethodAnnotation;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年2月16日 下午8:23:11
 *
 */
@DemoAnnotation(desc = "demo1", priority = DemoAnnotation.Priority.HIGH)
@Controller
public class DemoController {
	/**
	 *
	 */
	@DemoMethodAnnotation(desc = "demoMethodAnnotation")
	public void run() {
		System.out.println("DemoController 来了...");
	}

}
