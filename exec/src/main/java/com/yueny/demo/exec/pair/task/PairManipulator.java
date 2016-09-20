package com.yueny.demo.exec.pair.task;

import com.yueny.demo.exec.pair.AbstractPairManager;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月27日 下午11:52:57
 *
 */
public class PairManipulator implements Runnable {
	private final AbstractPairManager pm;

	public PairManipulator(final AbstractPairManager pm) {
		this.pm = pm;
	}

	@Override
	public void run() {
		while (true) {
			pm.increment();
		}
	}

	@Override
	public String toString() {
		return "Pair:" + pm.getPair() + ",checkCounter:"
				+ pm.checkCounter.get();
	}

}
