package com.yueny.demo.lamdba.test;

import org.junit.After;
import org.junit.Before;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年6月21日 下午9:48:05
 *
 */
@ContextConfiguration(locations = { "classpath*:/config/all-test.xml" })
// @TransactionConfiguration(transactionManager = "transactionManager",
// defaultRollback = true)
public abstract class AbstractBaseSpringTest extends BaseSpringTest {
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
