package com.yueny.demo.micros.cloud.observable;

import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class FlowableTest {
	@Test
	public void testFlowable() {
		// create a flowable
		final Flowable<String> flowable = getFlowable(" hello RxJava 2");

		// create
		final Subscriber<String> subscriber = new Subscriber<String>() {
			@Override
			public void onComplete() {
				System.out.println("执行结束 onComplete");
			}

			@Override
			public void onError(final Throwable t) {

			}

			@Override
			public void onNext(final String s) {
				// onNext方法里面传入的参数就是Flowable中发射出来的T
				System.out.println("正在执行 " + s);
			}

			@Override
			public void onSubscribe(final Subscription s) {
				// 调用request去请求资源，参数就是要请求的数量，一般如果不限制请求数量，可以写成Long.MAX_VALUE
				// 如果不调用request，Subscriber的onNext和onComplete方法将不会被调用。
				s.request(Long.MAX_VALUE);
			}
		};

		// 订阅代码 执行
		flowable.subscribe(subscriber);
	}

	@Test
	public void testFlowable2() {
		final Flowable<String> flowable = getFlowable("hello RxJava 2");

		// 对于 Subscriber 来说，我们目前仅仅关心onNext方法。所以可以简写成下面这样。
		final Consumer<String> consumer = new Consumer<String>() {
			@Override
			public void accept(final String s) throws Exception {
				System.out.println("正在执行 " + s);
			}
		};

		// 订阅代码 执行
		flowable.subscribe(consumer);
	}

	@Test
	public void testFlowable3() {
		// 如果省去单独定义变量，最终可以写成下面这样。
		getFlowable("hello RxJava 2").subscribe(new Consumer<String>() {
			@Override
			public void accept(final String s) throws Exception {
				System.out.println("正在执行 " + s);
			}
		});
	}

	/**
	 * 操作符<br>
	 * 操作符是为了解决 Flowable 对象变换问题而设计的，操作符可以在传递的途中对数据进行修改。 RxJava提供了很多实用的操作符。比如 map
	 * 操作符，可以把一个事件转换成另一个事件。操作符是为了解决 Flowable 对象变换问题而设计的，操作符可以在传递的途中对数据进行修改。
	 * RxJava提供了很多实用的操作符。比如 map 操作符，可以把一个事件转换成另一个事件。
	 */
	@Test
	public void testFlowable4() {
		Flowable.just("data1", "data2").map(new Function<String, String>() {
			@Override
			public String apply(final String s) throws Exception {
				return s + " -yueny";
			}
		}).subscribe(new Consumer<String>() {
			@Override
			public void accept(final String s) throws Exception {
				System.out.println("正在执行 " + s);
			}
		});
	}

	private <T> Flowable<T> getFlowable(final T t) {
		/* method 1 */
		// final Flowable<T> flowable = Flowable.create(new
		// FlowableOnSubscribe<T>() {
		// @Override
		// public void subscribe(final FlowableEmitter<T> e) throws Exception {
		// e.onNext(t);
		// e.onComplete();
		// }
		// }, BackpressureStrategy.BUFFER);

		/* method 2 */
		final Flowable<T> flowable = Flowable.just(t);

		return flowable;
	}

}
