package com.yueny.demo.micros.cloud.observable;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.schedulers.Schedulers;

public class Flowable2Test {
	@Test
	public void testFlowable() {
		final Flowable<Integer> upstream = getFlowable1(Arrays.asList(1, 2, 3)).subscribeOn(Schedulers.io());

		final Subscriber<Integer> downstream = new Subscriber<Integer>() {
			@Override
			public void onComplete() {
				System.out.println("执行结束 onComplete");
			}

			@Override
			public void onError(final Throwable t) {
				// .
			}

			@Override
			public void onNext(final Integer s) {
				// onNext方法里面传入的参数就是Flowable中发射出来的T
				System.out.println("正在执行 " + s);
			}

			@Override
			public void onSubscribe(final Subscription s) {
				System.out.println("onSubscribe");

				// 下游处理事件的能力
				// 调用request去请求资源，参数就是要请求的数量，一般如果不限制请求数量，可以写成Long.MAX_VALUE
				// 如果不调用request，Subscriber的onNext和onComplete方法将不会被调用。

				// 调用Subscription.cancel()也可以切断水管, 不同的地方在于Subscription增加了一个void
				// request(Long.MAX_VALUE)方法
				s.request(2L);
			}
		};

		// 订阅代码 执行
		upstream.observeOn(new ExecutorScheduler(Executors.newFixedThreadPool(3))).subscribe(downstream);

		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (final InterruptedException e) {
			System.out.println("sleep Interrupted!");
		}
	}

	@Test
	public void testFlowable2() {
		Flowable.interval(1, TimeUnit.MICROSECONDS)
				// 加上背压策略
				.onBackpressureDrop().observeOn(new ExecutorScheduler(Executors.newFixedThreadPool(3)))
				.subscribe(new Subscriber<Long>() {
					@Override
					public void onComplete() {
						System.out.println("onComplete");
					}

					@Override
					public void onError(final Throwable t) {
						System.out.println("onError:" + t);
					}

					@Override
					public void onNext(final Long aLong) {
						System.out.println("onNext ：" + aLong);
						try {
							Thread.sleep(500); // 延时0.5秒
						} catch (final InterruptedException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onSubscribe(final Subscription s) {
						System.out.println("onSubscribe");
						s.request(Long.MAX_VALUE);
					}
				});

		try {
			TimeUnit.MILLISECONDS.sleep(10000);
		} catch (final InterruptedException e) {
			System.out.println("sleep Interrupted!");
		}
	}

	@Test
	public void testFlowable3() {
		final Flowable<Integer> upstream = Flowable.create(new FlowableOnSubscribe<Integer>() {
			@Override
			public void subscribe(final FlowableEmitter<Integer> emitter) throws Exception {
				System.out.println("下游可以受理的请求量:" + emitter.requested());

				boolean flag;
				for (int i = 0;; i++) {
					flag = false;
					while (emitter.requested() == 0) {
						if (!flag) {
							System.out.println("Oh no! I can't emit value!");
							flag = true;
						}
					}

					emitter.onNext(i);
					System.out.println("emit " + i + " , requested = " + emitter.requested());
				}
			}
		}, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io());

		final Subscriber<Integer> downstream = new Subscriber<Integer>() {
			@Override
			public void onComplete() {
				System.out.println("执行结束 onComplete");
			}

			@Override
			public void onError(final Throwable t) {
				// .
			}

			@Override
			public void onNext(final Integer s) {
				// onNext方法里面传入的参数就是Flowable中发射出来的T
				System.out.println("正在执行 " + s);
			}

			@Override
			public void onSubscribe(final Subscription s) {
				System.out.println("onSubscribe");

				// 下游处理事件的能力
				// 调用request去请求资源，参数就是要请求的数量，一般如果不限制请求数量，可以写成Long.MAX_VALUE
				// 如果不调用request，Subscriber的onNext和onComplete方法将不会被调用。

				// 调用Subscription.cancel()也可以切断水管, 不同的地方在于Subscription增加了一个void
				// request(Long.MAX_VALUE)方法
				s.request(96L);
			}
		};

		// 订阅代码 执行
		upstream.observeOn(new ExecutorScheduler(Executors.newFixedThreadPool(3))).subscribe(downstream);

		try {
			TimeUnit.MILLISECONDS.sleep(10000);
		} catch (final InterruptedException e) {
			System.out.println("sleep Interrupted!");
		}
	}

	private <T> Flowable<T> getFlowable(final T t) {
		/* method 2 */
		final Flowable<T> upstream = Flowable.just(t);

		return upstream;
	}

	private <T> Flowable<T> getFlowable1(final List<T> ts) {
		/* method 1 */
		final Flowable<T> upstream = Flowable.create(new FlowableOnSubscribe<T>() {
			@Override
			public void subscribe(final FlowableEmitter<T> emitter) throws Exception {
				System.out.println("下游可以受理的请求量:" + emitter.requested());

				for (final T t : ts) {
					emitter.onNext(t);
				}

				emitter.onComplete();
			}
		}, BackpressureStrategy.BUFFER);

		return upstream;
	}

}
