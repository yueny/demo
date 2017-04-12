package com.yueny.demo.downlatch;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.yueny.demo.BaseSpringTest;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年3月22日 下午1:24:52
 *
 */
public class CountDownLatchDemoTest extends BaseSpringTest {
	@Autowired
	private CountDownLatchDemoService countDownLatchDemo;

	@Test
	public void testMethodRun() {
		final List<String> transIdList = Lists.newArrayList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11",
				"12");
		final String message = "[close timeout trans]";

		try {
			countDownLatchDemo.recover(transIdList, message);
		} catch (InstantiationException | IllegalAccessException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
