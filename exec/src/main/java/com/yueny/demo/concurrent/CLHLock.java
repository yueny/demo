package com.yueny.demo.concurrent;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;

/**
 * 阻塞锁<br>
 * CLH锁修改而成
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月11日 下午1:00:25
 *
 */
public class CLHLock {
	public static class CLHNode {
		private volatile Thread isLocked;
	}

	private static final ThreadLocal<CLHNode> LOCAL = new ThreadLocal<>();
	private static final AtomicReferenceFieldUpdater<CLHLock, CLHNode> UPDATER = AtomicReferenceFieldUpdater
			.newUpdater(CLHLock.class, CLHNode.class, "tail");
	@SuppressWarnings("unused")
	private volatile CLHNode tail;

	public void lock() {
		final CLHNode node = new CLHNode();
		LOCAL.set(node);
		CLHNode preNode = UPDATER.getAndSet(this, node);
		if (preNode != null) {
			preNode.isLocked = Thread.currentThread();
			LockSupport.park(this);
			preNode = null;
			LOCAL.set(node);
		}
	}

	public void unlock() {
		CLHNode node = LOCAL.get();
		if (!UPDATER.compareAndSet(this, node, null)) {
			System.out.println("unlock\t" + node.isLocked.getName());

			LockSupport.unpark(node.isLocked);
		}
		node = null;
	}
}
