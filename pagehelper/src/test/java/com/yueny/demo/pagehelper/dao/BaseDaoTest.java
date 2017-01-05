package com.yueny.demo.pagehelper.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:config/dao/demo-dao-test.xml" })
// As of Spring Framework 4.2
@Rollback(value = true) // @Rollback(false). equeals @Commit
@Transactional(value = "transactionManager")
@ActiveProfiles("dev")
public class BaseDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
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
