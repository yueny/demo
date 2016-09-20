package com.yueny.demo.frequence;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月15日 下午9:28:53
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@ContextConfiguration(locations = { "/config/frequence-config-test.xml" })
// @TransactionConfiguration(transactionManager = "transactionManager",
// defaultRollback = true)
// extends AbstractTransactionalJUnit4SpringContextTests
public abstract class BaseSpringTest extends AbstractJUnit4SpringContextTests {
	protected static final Logger logger = LoggerFactory.getLogger(BaseSpringTest.class);

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
