package com.yueny.demo.lamdba.test.mock;

import org.junit.Before;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于mock的测试用例
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年6月21日 下午9:46:21
 *
 */
public abstract class BaseMockTest {
	/**
	 *
	 */
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Before
	public final void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		// 通过反射进行一些服务的注入
		setUpBefore();
	}

	/**
	 * 通过反射进行一些服务的注入
	 *
	 * @throws Exception
	 */
	public void setUpBefore() throws Exception {
		// .
	}
}
