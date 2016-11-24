package com.yueny.demo.job;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年11月16日 上午9:48:39
 *
 */
public class Application {
	public static void main(final String[] args) {
		final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("config/all.xml");
		context.start();
	}
}
