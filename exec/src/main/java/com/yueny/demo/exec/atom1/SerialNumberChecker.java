package com.yueny.demo.exec.atom1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.yueny.demo.annotion.UnSafe;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月24日 下午7:44:54
 *
 */
public class SerialNumberChecker {
	@UnSafe
	static class SerialChecker implements Runnable {
		@Override
		public void run() {
			final int serial = SerialNumberGenerator.nextSerialNumber();

			if (serials.contains(serial)) {
				System.out.println("Duplicate :" + serial);
				// 出现重复数字则退出 exit
				System.exit(0);
			}
			serials.add(serial);
		}

	}

	private static CircularSet serials = new CircularSet(1000);

	private static final int SIZE = 1000;

	final static ExecutorService exec = Executors.newCachedThreadPool();

	public static void main(final String[] args) throws NumberFormatException,
			InterruptedException {
		for (int i = 0; i < SIZE; i++) {
			exec.execute(new SerialChecker());
		}

		// stop after n seconds if there's an argument
		if (args.length > 0) {
			TimeUnit.SECONDS.sleep(new Integer(args[0]));

			System.out.println("NO Duplicates detected");
			System.exit(0);
		}
	}

}
