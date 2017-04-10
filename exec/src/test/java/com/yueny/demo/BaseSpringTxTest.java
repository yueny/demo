package com.yueny.demo;

import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;

@ContextConfiguration(locations = { "classpath*:config/all-test.xml" }, initializers = { TestApplicationContextInitializer.class })
@ActiveProfiles("dev")
public class BaseSpringTxTest extends AbstractTransactionalJUnit4SpringContextTests {
	// AbstractTransactionalTestNGSpringContextTests {
	// AbstractTransactionalJUnit4SpringContextTests{

	protected final Logger log = LoggerFactory.getLogger(getClass());

	@AfterTransaction
	public void afterTransaction() {
		log.info("事务结束");
	}

	@BeforeTransaction
	public void beforeTransaction() {
		log.info("事务开始");
	}

	@Before
	public void setUp() throws Exception {
		log.info("测试开始");
	}

	@After
	public void tearDown() throws Exception {
		log.info("测试结束");
	}

}
