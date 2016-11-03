package com.yueny.demo.metrics.scheduler.task;

import java.util.Date;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年10月27日 上午11:58:55
 *
 */
public class DelayTask implements Runnable, Delayed {
	/**
	 * 任务延时执行时间,默认1秒
	 */
	private long executeTime = 1000L;

	public DelayTask() {
		// .
	}

	/**
	 * @param lastExecuteTime
	 *            上次执行时间
	 * @param delaySeconds
	 *            延迟秒数
	 */
	public DelayTask(final Date lastExecuteTime, final Long delaySeconds) {
		super();
		this.executeTime = getExecuteTime(lastExecuteTime, delaySeconds);
	}

	@Override
	public int compareTo(final Delayed o) {
		final DelayTask task = (DelayTask) o;
		return executeTime > task.executeTime ? 1 : (executeTime < task.executeTime ? -1 : 0);
	}

	@Override
	public long getDelay(final TimeUnit unit) {
		return unit.convert(executeTime - System.currentTimeMillis(), TimeUnit.SECONDS);
	}

	/**
	 * @param lastExecuteTime
	 *            上次执行时间
	 * @param delaySeconds
	 *            延迟秒数
	 */
	private long getExecuteTime(final Date lastExecuteTime, final Long delaySeconds) {
		final long lastTime = lastExecuteTime.getTime();
		return (delaySeconds == null ? 0 : delaySeconds * 1000) + lastTime;
	}

	@Override
	public void run() {
		try {
			System.out.println("去通知");
			TimeUnit.MILLISECONDS.sleep(1 * 1000);
		} catch (final Exception e) {

		}
	}

}
