package com.yueny.demo.micros.cloud.observable;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.schedulers.Schedulers;

public class ObservableTest {
	@Test
	public void testObservable() {
		// 创建一个上游 Observable：
		final Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
			// emitter: 发射器
			@Override
			public void subscribe(final ObservableEmitter<Integer> emitter) throws Exception {
				emitter.onNext(1);
				emitter.onNext(2);
				emitter.onNext(3);

				// onComplete和onError必须唯一并且互斥, 即不能发多个onComplete, 也不能发多个onError,
				// 也不能先发一个onComplete, 然后再发一个onError, 反之亦然
				emitter.onComplete();
			}
		});

		// 创建一个下游 Observer
		final Observer<Integer> observer = new Observer<Integer>() {
			@Override
			public void onComplete() {
				System.out.println("complete");
			}

			@Override
			public void onError(final Throwable e) {
				System.out.println("error");
			}

			@Override
			public void onNext(final Integer value) {
				// onNext方法里面传入的参数就是Flowable中发射出来的T
				System.out.println("正在执行 " + value);
			}

			@Override
			public void onSubscribe(final Disposable d) {
				System.out.println("onSubscribe init");
				// 调用了subscribe() 方法之后开始发送事件.
			}
		};

		// 订阅代码 执行 , 建立连接
		observable.subscribe(observer);
	}

	/**
	 * 链式操作
	 */
	@Test
	public void testObservable1() {
		Observable.create(new ObservableOnSubscribe<Integer>() {
			@Override
			public void subscribe(final ObservableEmitter<Integer> emitter) throws Exception {
				System.out.println("Observable thread is : " + Thread.currentThread().getName());

				emitter.onNext(1);
				emitter.onNext(2);
				emitter.onNext(3);

				emitter.onComplete();
			}
		}).subscribe(new Observer<Integer>() {
			@Override
			public void onComplete() {
				System.out.println("complete");
			}

			@Override
			public void onError(final Throwable e) {
				System.out.println("error");
			}

			@Override
			public void onNext(final Integer value) {
				// onNext方法里面传入的参数就是Flowable中发射出来的T
				System.out.println("正在执行 " + value);
			}

			@Override
			public void onSubscribe(final Disposable d) {
				System.out.println("onSubscribe init");
				// 调用了subscribe() 方法之后开始发送事件.
			}
		});
	}

	/**
	 * 只关心onNext
	 */
	@Test
	public void testObservable2() {
		System.out.println("main thread is : " + Thread.currentThread().getName());

		final Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
			@Override
			public void subscribe(final ObservableEmitter<Integer> emitter) throws Exception {
				System.out.println("Observable thread is : " + Thread.currentThread().getName());

				emitter.onNext(1);
				emitter.onNext(2);
				emitter.onNext(3);
			}
		});

		final Consumer<Integer> consumer = new Consumer<Integer>() {
			@Override
			public void accept(final Integer value) throws Exception {
				System.out.println("Consumer thread is :" + Thread.currentThread().getName());

				System.out.println("正在执行 " + value);
			}
		};

		/*
		 * Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作
		 * Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作
		 * Schedulers.newThread() 代表一个常规的新线程
		 */
		observable
				// 指定的是上游发送事件的线程. 多次指定上游的线程只有第一次指定的有效,
				// 也就是说多次调用subscribeOn() 只有第一次的有效, 其余的会被忽略.
				.subscribeOn(Schedulers.newThread())
				// 指定的是下游接收事件的线程. 多次指定下游的线程是可以的,
				// 也就是说每调用一次observeOn() , 下游的线程就会切换一次.
				.observeOn(new ExecutorScheduler(Executors.newFixedThreadPool(3))).doOnNext(new Consumer<Integer>() {
					@Override
					public void accept(final Integer integer) throws Exception {
						System.out.println("After observeOn(newSingleThreadExecutor), current thread is: "
								+ Thread.currentThread().getName());
					}
				}).subscribe(consumer);

		try {
			TimeUnit.MILLISECONDS.sleep(200);
		} catch (final InterruptedException e) {
			System.out.println("sleep Interrupted!");
		}
	}

}
