package com.yueny.demo.micros.boot.spring.configure.tester;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

// SpringJUnit支持，由此引入Spring-Test框架支持！
@RunWith(SpringJUnit4ClassRunner.class)
// 指定我们SpringBoot工程的Application启动类
@ContextConfiguration(classes = TesterForServiceContextConfiguration.class)
public abstract class ServiceTester {
	protected static final Logger logger = LoggerFactory.getLogger(ServiceTester.class);
	@Autowired
	private WebApplicationContext webApplicationContext;
	protected MockMvc mvc;

	@Before
	public void setUp() throws Exception {
		// 初始化测试用例类中由Mockito的注解标注的所有模拟对象
		MockitoAnnotations.initMocks(this);

		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

}
