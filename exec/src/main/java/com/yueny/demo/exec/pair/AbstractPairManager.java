package com.yueny.demo.exec.pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Protect a Pair iside a thread-safe class
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月27日 下午11:15:56
 *
 */
public abstract class AbstractPairManager {
	private final List<Pair> storage = Collections
			.synchronizedList(new ArrayList<Pair>());
	protected Pair pair = new Pair();
	public AtomicInteger checkCounter = new AtomicInteger(0);

	// synchronized的
	public synchronized Pair getPair() {
		// Make a copy to keep the original safe
		return new Pair(pair.getX(), pair.getY());
	}

	protected void store(final Pair p) {
		storage.add(p);

		try {
			TimeUnit.SECONDS.sleep(50);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	public abstract void increment();
}
