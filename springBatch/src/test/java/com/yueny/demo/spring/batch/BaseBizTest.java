package com.yueny.demo.spring.batch;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/all-test.xml" })
public abstract class BaseBizTest extends AbstractTransactionalTestNGSpringContextTests {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@AfterTransaction
	public void afterTransaction() {
		logger.info("事务结束");
	}

	@BeforeTransaction
	public void beforeTransaction() {
		logger.info("事务开始");
	}

	@Before
	public void setUp() throws Exception {
		logger.info("测试开始");
	}

	@After
	public void tearDown() throws Exception {
		logger.info("测试结束");
	}

}
