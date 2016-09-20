package com.yueny.demo.mq.run;

import java.util.Queue;

public class ConcumerRunnable implements Runnable {
	private final Queue<String> queue;

	public ConcumerRunnable(final Queue<String> queue) {
		this.queue = queue;
	}

	public String get() {
		if (queue.isEmpty()) {
			return null;
		}
		return queue.poll();
	}

	@Override
	public void run() {
		while (true) {
			final String str = get();
			if (str != null) {
				System.out.println(Thread.currentThread().getName() + " 取数据："
						+ str);
				continue;
			}

			System.out.println(Thread.currentThread().getName() + " 任务池为空！");
			try {
				Thread.sleep(1 * 1000L);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
