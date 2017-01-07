package com.yueny.demo.capture;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:config/config-test.xml" })
@ActiveProfiles("dev")
public class BaseBizTest extends AbstractJUnit4SpringContextTests {
	protected final Logger log = LoggerFactory.getLogger(getClass());

	@Before
	public void setUp() throws Exception {
		log.info("测试开始");
	}

	@After
	public void tearDown() throws Exception {
		log.info("测试结束");
	}

}
