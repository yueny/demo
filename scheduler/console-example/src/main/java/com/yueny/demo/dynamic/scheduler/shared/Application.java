package com.yueny.demo.dynamic.scheduler.shared;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class Application implements InitializingBean {

	// Current development version
	public static final String CURRENT_VERSION = "0.1";

	// 系统字符编码
	public static final String ENCODING = "UTF-8";
	public static final String PROJECT_HOME = "https://git.oschina.net/mkk/HeartBeat/";

	/*
	 * default
	 */
	public Application() {
		// .
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(PROJECT_HOME, "host is null");
	}

}
