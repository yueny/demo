package com.yueny.demo.exec.pair;

/**
 * use a critical section
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月27日 下午11:49:50
 *
 */
public class CirticalPairManager extends AbstractPairManager {

	@Override
	public void increment() {
		Pair temp;

		synchronized (this) {
			pair.incrementX();
			pair.incrementY();

			temp = getPair();
		}

		store(temp);
	}

}
