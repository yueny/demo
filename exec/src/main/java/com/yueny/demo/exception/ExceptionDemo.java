package com.yueny.demo.exception;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExceptionDemo {
	public static void main(final String[] args) {
		final ExecutorService publicMatcherIrregularExecutor = Executors.newFixedThreadPool(36);
		final CompletionService<Long> execComp = new ExecutorCompletionService<Long>(publicMatcherIrregularExecutor);

		for (int i = 0; i < 5; i++) {
			final Callable<Long> call = new MatRunner();
			execComp.submit(call);
		}

		for (int i = 0; i < 5; i++) {
			try {
				final Future<Long> future = execComp.take();
				System.out.println(future.get());
			} catch (InterruptedException | ExecutionException e) {
				log.error("execute Irregular matcher future in executor exception, maybe future time out", e);
			}
		}

	}
}
