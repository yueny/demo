package com.yueny.demo.metrics;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @param <T>
 *            待测试实体类型
 * @author 袁洋 2015年8月10日 下午4:29:14
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/metrics-service-test.xml" })
public class BaseBizTest {
	// .
}
