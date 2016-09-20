package com.yueny.demo.mq;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.yueny.demo.mq.run.ConcumerRunnable;
import com.yueny.demo.mq.run.InsertThreadRunnable;

/**
 * java多线程消费消息队列
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年2月26日 下午10:40:50
 *
 */
public class Main {
	private final static ExecutorService concumerExec = Executors
			.newCachedThreadPool();
	private final static ExecutorService exec = Executors
			.newSingleThreadExecutor();
	private static Queue<String> queue = new ConcurrentLinkedQueue<String>();
	private static final int threadNum = 3;

	public static void main(final String[] args) {
		final InsertThreadRunnable it = new InsertThreadRunnable(queue);
		exec.execute(it);

		for (int i = 0; i < threadNum; i++) {
			final ConcumerRunnable concumer = new ConcumerRunnable(queue);
			concumerExec.execute(concumer);
		}

		while (true) {
			try {
				Thread.sleep(100000L);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
