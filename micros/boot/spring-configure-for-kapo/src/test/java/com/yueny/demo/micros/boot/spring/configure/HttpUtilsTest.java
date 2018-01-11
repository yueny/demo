package com.yueny.demo.micros.boot.spring.configure;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.gson.reflect.TypeToken;
import com.yueny.demo.micros.boot.spring.configure.api.request.JobForMatcherRequest;
import com.yueny.rapid.data.resp.pojo.response.NormalResponse;
import com.yueny.rapid.lang.thread.factory.NamedThreadFactory;

public class HttpUtilsTest {
	private static final String serviceUrl = "http://localhost:8090";

	@Test
	public void testGet() {
		final ExecutorService executor = Executors.newFixedThreadPool(5,
				new NamedThreadFactory("MatcherPublicExecutor", true));

		final int count = 30;
		final List<Future<NormalResponse<String>>> fs = Lists.newArrayList();
		final CountDownLatch matcherRootLatch = new CountDownLatch(count);
		for (int i = 0; i < count; i++) {
			final Future<NormalResponse<String>> future = executor.submit(new Callable<NormalResponse<String>>() {
				@Override
				public NormalResponse<String> call() throws Exception {
					try {
						final NormalResponse<String> resp = HttpUtils.get(serviceUrl + "/service/111", "",
								new TypeToken<NormalResponse<String>>() {
								});

						return resp;
					} finally {
						if (matcherRootLatch != null) {
							matcherRootLatch.countDown();
						}
					}
				}
			});

			fs.add(future);
		}

		try {
			matcherRootLatch.await();
		} catch (final InterruptedException e1) {
			e1.printStackTrace();
		}

		for (final Future<NormalResponse<String>> future : fs) {
			try {
				final NormalResponse<String> resp = future.get(60, TimeUnit.SECONDS);

				System.out.println(resp);
			} catch (InterruptedException | ExecutionException | TimeoutException e) {
				e.printStackTrace();
			}
		}
	}

	@Test
	public void testPost() {
		final ExecutorService executor = Executors.newFixedThreadPool(5,
				new NamedThreadFactory("MatcherPublicExecutor", true));

		final int count = 1;
		final List<Future<NormalResponse<JobForMatcherRequest>>> fs = Lists.newArrayList();
		final CountDownLatch matcherRootLatch = new CountDownLatch(count);
		for (int i = 0; i < count; i++) {
			final Future<NormalResponse<JobForMatcherRequest>> future = executor
					.submit(new Callable<NormalResponse<JobForMatcherRequest>>() {
						@Override
						public NormalResponse<JobForMatcherRequest> call() throws Exception {
							try {
								final JobForMatcherRequest req = new JobForMatcherRequest();
								req.setBorrowRound(20);
								req.setDelay(5L);
								req.setLendRound(20);
								req.setMask("测试啊");
								req.setTimer(new Date());

								final NormalResponse<JobForMatcherRequest> resp = HttpUtils.post("/post/submit", req,
										new TypeToken<NormalResponse<JobForMatcherRequest>>() {
										});

								return resp;
							} finally {
								if (matcherRootLatch != null) {
									matcherRootLatch.countDown();
								}
							}
						}
					});

			fs.add(future);
		}

		try {
			matcherRootLatch.await();
		} catch (final InterruptedException e1) {
			e1.printStackTrace();
		}

		for (final Future<NormalResponse<JobForMatcherRequest>> future : fs) {
			try {
				final NormalResponse<JobForMatcherRequest> resp = future.get(60, TimeUnit.SECONDS);

				System.out.println(resp);
			} catch (InterruptedException | ExecutionException | TimeoutException e) {
				e.printStackTrace();
			}
		}
	}
}
