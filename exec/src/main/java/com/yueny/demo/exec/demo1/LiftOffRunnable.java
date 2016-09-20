package com.yueny.demo.exec.demo1;

import com.yueny.demo.annotion.UnSafe;

@UnSafe
public class LiftOffRunnable implements Runnable {
	private static int taskCount = 1;
	private int countDown = 10;
	private final int id = taskCount++;

	@Override
	public void run() {
		while (countDown-- > 0) {
			System.out.print(get());
			Thread.yield();
		}
	}

	private String get() {
		return "#" + id + "(" + (countDown > 0 ? countDown : "END!") + ").";
	}
}
