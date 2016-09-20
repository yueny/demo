package com.yueny.demo.exec.pair;

import com.yueny.demo.annotion.Safe;

/**
 * Synchronize the entire method
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月27日 下午11:46:21
 *
 */
@Safe
public class DeafultPairManager extends AbstractPairManager {

	@Override
	public synchronized void increment() {
		pair.incrementX();
		pair.incrementY();

		store(getPair());
	}

}
