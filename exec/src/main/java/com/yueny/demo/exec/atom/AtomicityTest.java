package com.yueny.demo.exec.atom;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.yueny.demo.annotion.UnSafe;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月24日 下午7:44:54
 *
 */
@UnSafe
public class AtomicityTest implements Runnable {
	public static void main(final String[] args) {
		final ExecutorService exec = Executors.newCachedThreadPool();
		final AtomicityTest at = new AtomicityTest();
		exec.execute(at);

		while (true) {
			final int val = at.getValue();
			if (val % 2 != 0) {
				// 找到奇数则输出，并退出
				System.out.println("not normal:" + val);
				System.exit(0);
			} else {
				if (val % 1000 == 0) {
					System.out.println("normal:" + val);
				}
			}
		}
	}

	private int i = 0;

	public int getValue() {// wrong
		// public synchronized int getValue() {//right
		return i;
	}

	@Override
	public void run() {
		while (true) {
			evenIncrement();
		}
	}

	private synchronized void evenIncrement() {
		i++;
		i++;
	}

}
