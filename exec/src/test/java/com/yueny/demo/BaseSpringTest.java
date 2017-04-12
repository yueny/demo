package com.yueny.demo;

import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = { "classpath*:config/all-test.xml" })
@ActiveProfiles("dev")
public class BaseSpringTest extends AbstractJUnit4SpringContextTests {
	protected final Logger log = LoggerFactory.getLogger(getClass());

	@Before
	public void setUp() throws Exception {
		log.debug("测试开始");
	}

	@After
	public void tearDown() throws Exception {
		log.debug("测试结束");
	}

}
