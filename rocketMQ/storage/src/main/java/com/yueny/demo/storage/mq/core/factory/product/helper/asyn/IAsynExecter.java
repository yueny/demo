package com.yueny.demo.storage.mq.core.factory.product.helper.asyn;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年7月12日 下午7:08:43
 * @since 1.6.4
 */
public interface IAsynExecter {
	/**
	 * 服务关闭
	 */
	void shutdown();

	/**
	 * 服务启动
	 */
	void startup();

}
