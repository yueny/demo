package com.yueny.demo.micros.boot.spring.configure.controller;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.yueny.demo.micros.boot.spring.configure.tester.ServiceTester;

public class WelcomeControllerTest extends ServiceTester {
	@Test
	public void testHome() throws Exception {
		final String expectedResult = "hello world!";
		final String uri = "/";
		final MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		final int status = mvcResult.getResponse().getStatus();
		final String content = mvcResult.getResponse().getContentAsString();

		Assert.assertTrue("错误，正确的返回值为200", status == 200);
		Assert.assertFalse("错误，正确的返回值为200", status != 200);
		Assert.assertTrue("数据一致", expectedResult.equals(content));
		Assert.assertFalse("数据不一致", !expectedResult.equals(content));
	}

}
