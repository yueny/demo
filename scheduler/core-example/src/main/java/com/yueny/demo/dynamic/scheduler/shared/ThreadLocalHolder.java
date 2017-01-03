package com.yueny.demo.dynamic.scheduler.shared;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月29日 上午12:35:50
 *
 */
@Deprecated
public abstract class ThreadLocalHolder {
	// Filter中ThreadLocalHolder.clientIp(clientIp);；使用时ThreadLocalHolder.clientIp()
	private static ThreadLocal<String> clientIpThreadLocal = new ThreadLocal<>();

	public static String clientIp() {
		return clientIpThreadLocal.get();
	}

	public static void clientIp(final String clientIp) {
		clientIpThreadLocal.set(clientIp);
	}

	protected ThreadLocalHolder() {
		//
	}

}