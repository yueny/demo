package com.yueny.demo.downlatch.r;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.yueny.demo.BaseTest;
import com.yueny.demo.downlatch.CountDownLatchDemo;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年3月22日 下午1:24:52
 *
 */
public class CountDownLatchDemoTest extends BaseTest {
	@Autowired
	private CountDownLatchDemo countDownLatchDemo;

	@Test
	public void testMethodRun() {
		final List<String> transIdList = Lists.newArrayList("11", "22", "33",
				"44", "55", "66", "77", "88", "99", "100");
		final String message = "[close timeout trans]";

		try {
			countDownLatchDemo.recover(transIdList, message);
		} catch (InstantiationException | IllegalAccessException
				| InterruptedException e) {
			e.printStackTrace();
		}
	}

}
