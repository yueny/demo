package com.yueny.demo.exec.atom;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import com.yueny.demo.annotion.Safe;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月24日 下午7:44:54
 *
 */
@Safe
public class AtomicityIntegerTest implements Runnable {
	public static void main(final String[] args) {
		// terminate atfer 5 seconds
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				System.err.println("Aborting");
				System.exit(0);
			}
		}, 5000);

		final ExecutorService exec = Executors.newCachedThreadPool();
		final AtomicityIntegerTest ait = new AtomicityIntegerTest();
		exec.execute(ait);

		while (true) {
			final int val = ait.getValue();
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

	private final AtomicInteger i = new AtomicInteger(0);

	public int getValue() {
		return i.get();
	}

	@Override
	public void run() {
		while (true) {
			evenIncrement();
		}
	}

	private void evenIncrement() {
		i.addAndGet(2);
	}

}
