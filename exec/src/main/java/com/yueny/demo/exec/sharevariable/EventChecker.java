package com.yueny.demo.exec.sharevariable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.yueny.demo.exec.sharevariable.generator.AbstractIntGenerator;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月24日 下午7:44:54
 *
 */
public class EventChecker implements Runnable {
	public static void test(final AbstractIntGenerator gp) {
		test(gp, 10);
	}

	public static void test(final AbstractIntGenerator gp, final int count) {
		final ExecutorService exec = Executors.newCachedThreadPool();

		for (int i = 0; i < count; i++) {
			exec.execute(new EventChecker(gp, i));
		}
	}

	private final int id;

	private final AbstractIntGenerator intGenerator;

	public EventChecker(final AbstractIntGenerator ig, final int ident) {
		intGenerator = ig;
		id = ident;
	}

	@Override
	public void run() {
		while (!intGenerator.isCanceled()) {
			final int val = intGenerator.next();

			if (val % 2 != 0) {
				System.out.println(val + " not even!");
				intGenerator.cancel();
			} else {
				if (val % 100 == 0) {
					System.out.println(val + " 正常!");
				}
			}
		}
	}

}
