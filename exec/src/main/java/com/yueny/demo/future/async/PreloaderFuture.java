package com.yueny.demo.future.async;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import lombok.Getter;

/**
 * Created by yueny on 2017/4/13 0013.
 */
public class PreloaderFuture implements Callable<Long> {
	private final CountDownLatch latch;
	@Getter
	private final List<String> transIdList;

	public PreloaderFuture(final List<String> transIdList, final CountDownLatch latch) {
		this.transIdList = transIdList;
		this.latch = latch;
	}

	@Override
	public Long call() throws Exception {
		try {
			return 8L;
		} catch (final Exception e) {
			throw e;
		} finally {
			latch.countDown();
		}

	}

}
