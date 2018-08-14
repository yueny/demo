/**
 *
 */
package com.yueny.demo.sharding.example.service.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yueny.demo.sharding.example.bo.ModifyDemoBo;
import com.yueny.demo.sharding.example.service.BaseBizTest;
import com.yueny.demo.sharding.example.service.IModifyDemoService;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月5日 上午11:05:44
 *
 */
public class ModifyDemoServiceImplTest extends BaseBizTest {
	@Autowired
	private IModifyDemoService demoService;

	@Test
	public void testQueryAll() {
		final List<ModifyDemoBo> lists = demoService.queryAll();

		Assert.assertTrue(lists.size() > 1);
	}
}
