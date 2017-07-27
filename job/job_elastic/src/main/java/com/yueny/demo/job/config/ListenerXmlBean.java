package com.yueny.demo.job.config;

import io.github.xdiamond.client.annotation.AllKeyListener;
import io.github.xdiamond.client.annotation.OneKeyListener;
import io.github.xdiamond.client.event.ConfigEvent;

/**
 *
 * @author hengyunabc
 *
 */
// @Service
@Deprecated
public class ListenerXmlBean {

	@AllKeyListener
	public void testAllKeyListener(final ConfigEvent event) {
		System.err.println("ListenerXmlBean, testAllKeyListener, event :" + event);
	}

	@OneKeyListener(key = "testOneKeyListener")
	public void testOneKeyListener(final ConfigEvent event) {
		System.err.println("ListenerXmlBean, testOneKeyListener, event :" + event);
		// // 配置变更异步通知
		// try {
		// final Properties properties = new Properties();
		// properties.load(new StringReader(configInfo));
		//
		// System.out.println("更新了：" + configInfo);
		// } catch (final Throwable ignore) {
		// // .
		// }
	}

}
