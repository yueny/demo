package com.yueny.demo.exec.demo2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.yueny.demo.exec.demo1.LiftOffRunnable;

public class CacheThreadPool {
	public static void main(final String[] args) {
		final ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < 5; i++) {
			exec.execute(new LiftOffRunnable());
		}
		System.out.println("我的任务结束了！");
		exec.shutdown();
	}
}
