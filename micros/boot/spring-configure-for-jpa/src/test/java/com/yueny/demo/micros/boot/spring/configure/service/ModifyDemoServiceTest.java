package com.yueny.demo.micros.boot.spring.configure.service;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import com.yueny.demo.micros.boot.spring.configure.entry.ModifyDemoForJpaEntry;
import com.yueny.demo.micros.boot.spring.configure.tester.ServiceTester;

public class ModifyDemoServiceTest extends ServiceTester {
	@Resource
	private IModifyDemoService modifyDemoService;

	@Test
	public void testQueryById() {
		final ModifyDemoForJpaEntry en = modifyDemoService.queryById(1L);

		Assert.assertNotNull(en);
	}

	@Test
	public void testQueryByType() {
		final List<ModifyDemoForJpaEntry> ts = modifyDemoService.queryByType("Y");

		Assert.assertTrue("数据不一致", ts.size() > 0);
	}

}
