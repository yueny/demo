package com.yueny.demo.concurrent.clazz;

/**
 * 线程类ObjThread，用于启动同步方法（注意它的run方法可能会调整以进行不同的测试）
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月11日 下午1:22:46
 *
 */
public class ObjThread extends Thread {
	int i = 0;
	LockTestClass lock;

	public ObjThread(final LockTestClass lock, final int i) {
		this.lock = lock;
		this.i = i;
	}

	@Override
	public void run() {
		// 无锁方法
		// lock.noSynMethod(this.getId(),this);

		// 对象锁方法2,采用synchronized (this)来加锁
		lock.synInMethod();

		// 对象锁方法2，采用synchronized synOnMethod的方式
		lock.synOnMethod();

		// 私有锁方法，采用synchronized(object)的方式
		// lock.synMethodWithObj();

		// 类锁方法，采用static synchronized increment的方式
		// LockTestClass.increament();
	}

}
