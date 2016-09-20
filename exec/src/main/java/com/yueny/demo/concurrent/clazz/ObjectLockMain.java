package com.yueny.demo.concurrent.clazz;

/**
 * 启动类
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月11日 下午1:22:10
 *
 */
public class ObjectLockMain {
	public static void main(final String[] args) {
		System.out.println("start time = " + System.currentTimeMillis() + "ms");

		final LockTestClass test = new LockTestClass();
		for (int i = 0; i < 3; i++) {
			final Thread thread = new ObjThread(test, i);
			thread.start();
		}
	}
}
