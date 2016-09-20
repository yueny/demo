package com.yueny.demo.exec.pair.task;

import com.yueny.demo.exec.pair.AbstractPairManager;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月27日 下午11:52:57
 *
 */
public class PairChecker implements Runnable {
	private final AbstractPairManager pm;

	public PairChecker(final AbstractPairManager pm) {
		this.pm = pm;
	}

	@Override
	public void run() {
		while (true) {
			pm.checkCounter.incrementAndGet();
			pm.getPair().checkStatus();
		}
	}

}
