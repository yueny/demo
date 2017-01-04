/**
 *
 */
package com.yueny.demo.pagehelper.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yueny.demo.BaseTest;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月5日 上午11:05:44
 *
 */
public class ModifyDemoServiceImplTest extends BaseTest {
	@Autowired
	private IModifyDemoService demoService;

	@Test
	public void testQueryAll() {
		System.out.println(demoService.queryAll());
	}
}
