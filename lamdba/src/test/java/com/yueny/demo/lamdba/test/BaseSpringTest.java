package com.yueny.demo.lamdba.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于spring的测试用例
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年6月21日 下午9:46:42
 *
 */
// @RunWith(SpringJUnit4ClassRunner.class)
// @ActiveProfiles("dev")
public abstract class BaseSpringTest {
	// extends AbstractTransactionalJUnit4SpringContextTests
	/**
	 *
	 */
	protected final Logger logger = LoggerFactory.getLogger(getClass());

}
