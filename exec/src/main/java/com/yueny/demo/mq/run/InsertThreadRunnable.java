package com.yueny.demo.mq.run;

import java.util.Queue;

public class InsertThreadRunnable implements Runnable {
	private final Queue<String> queue;

	public InsertThreadRunnable(final Queue<String> queue) {
		this.queue = queue;
	}

	public boolean insert(final String str) throws Exception {
		System.out.println("insert=" + str + " , queue.size=" + queue.size());
		queue.offer(str);

		if (queue.size() % 2000 == 0) {
			// 测试，队列大于2000的时候暂停生产
			try {
				Thread.sleep(2 * 1000L);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	@Override
	public void run() {
		int i = 0;
		while (true) {
			try {
				insert("insert" + (i++));
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

}
