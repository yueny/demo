package com.yueny.demo.concurrent.clazz;

/**
 * 锁的测试类LockTestClass，包括各种加锁方法<br>
 * synchronized直接加在方法上和synchronized(this)都是对当前对象加锁，二者的加锁方法够成了竞争关系，同一时刻只能有一个方法能执行
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月11日 下午1:23:00
 *
 */
public class LockTestClass {
	// 用于类锁计数
	private static int clazzCount = 0;

	/**
	 * 类锁
	 */
	public static synchronized void increament() {
		System.out.println(
				"class synchronized. clazzCount = " + clazzCount + ", time = " + System.currentTimeMillis() + "ms");
		clazzCount++;
		try {
			Thread.sleep(2000L);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("class synchronized ends.");
	}

	// 私有锁
	private final Object object = new Object();

	/**
	 * &lt;p&gt; 无锁方法
	 *
	 * @param threadID
	 * @param thread
	 */
	public void noSynMethod(final long threadID, final ObjThread thread) {
		System.out.println("nosyn: class obj is " + thread + ", threadId is" + threadID);
	}

	/**
	 * 对象锁方法2,采用synchronized (this)来加锁
	 */
	public void synInMethod() {
		synchronized (this) {
			System.out.println("synInMethod begins" + ", time = " + System.currentTimeMillis() + "ms");
			try {
				Thread.sleep(2000L);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("synInMethod ends");
		}

	}

	/**
	 * 对象锁方法3,采用synchronized (object)的方式
	 */
	public void synMethodWithObj() {
		synchronized (object) {
			System.out.println("synMethodWithObj begins" + ", time = " + System.currentTimeMillis() + "ms");
			try {
				Thread.sleep(2000L);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("synMethodWithObj ends");
		}
	}

	/**
	 * 对象锁方法1,采用synchronized synOnMethod的方式
	 */
	public synchronized void synOnMethod() {
		System.out.println("synOnMethod begins" + ", time = " + System.currentTimeMillis() + "ms");
		try {
			Thread.sleep(2000L);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("synOnMethod ends");
	}
}
