package com.yueny.demo.micros.boot.spring.context.config.scheduler.factory;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.base.Joiner;

/**
 * 带有名称的 线程 工厂类
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年4月11日 下午4:52:16
 * @since 1.0.0
 */
public class NamedThreadFactory implements ThreadFactory {
	private static final AtomicInteger POOL_SEQ = new AtomicInteger(1);

	private final boolean daemon;

	private final ThreadGroup group;
	private final String prefix;
	private final AtomicInteger threadNum = new AtomicInteger(1);
	private UncaughtExceptionHandler uncaughtExceptionHandler;

	public NamedThreadFactory() {
		this("pool-" + POOL_SEQ.getAndIncrement(), false);
	}

	public NamedThreadFactory(final String prefix) {
		this(prefix, false);
	}

	public NamedThreadFactory(final String prefix, final boolean daemon) {
		this(prefix, false, null);
	}

	public NamedThreadFactory(final String prefix, final boolean daemon,
			final UncaughtExceptionHandler uncaughtExceptionHandler) {
		this.prefix = Joiner.on("-").join(prefix, "%s");// prefix + "-thread-";
		this.daemon = daemon;
		final SecurityManager s = System.getSecurityManager();
		group = (s == null) ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();

		if (uncaughtExceptionHandler != null) {
			this.uncaughtExceptionHandler = uncaughtExceptionHandler;
		}
	}

	public NamedThreadFactory(final String prefix, final UncaughtExceptionHandler uncaughtExceptionHandler) {
		this(prefix, false, uncaughtExceptionHandler);
	}

	public ThreadGroup getThreadGroup() {
		return group;
	}

	@Override
	public Thread newThread(final Runnable runnable) {
		final String name = prefix + threadNum.getAndIncrement();

		final Thread ret = new Thread(group, runnable, name, 0);
		if (uncaughtExceptionHandler != null) {
			ret.setUncaughtExceptionHandler(uncaughtExceptionHandler);
		}
		ret.setDaemon(daemon);

		return ret;
	}
}
