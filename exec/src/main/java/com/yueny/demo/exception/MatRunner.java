package com.yueny.demo.exception;

import java.util.concurrent.Callable;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MatRunner implements Callable<Long> {
	@Override
	public Long call() throws Exception {
		// try {
		// throw new NullPointerException();
		// } catch (final Exception e) {
		// log.error("error: ", e);
		// }

		return 0L;
	}

}
