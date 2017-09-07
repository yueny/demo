package com.yueny.demo.micros.boot.spring.configure;

import org.junit.Test;

import com.alibaba.druid.filter.config.ConfigTools;

public class ConfigToolsTest {
	@Test
	public void test() throws Exception {
		System.out.println(ConfigTools.encrypt("root"));
	}
}
