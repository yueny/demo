package com.yueny.demo.micros.cloud.observable;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.schedulers.Schedulers;

public class ObservableZipTest {
	@Test
	public void testObservable() {
		// 创建一个观察者
		final Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
			@Override
			public void subscribe(final ObservableEmitter<Integer> emitter) throws Exception {
				for (int i = 0; i < 10; i++) { // 无限循环发事件
					emitter.onNext(i);

					// 由于没有发送Complete事件, 因此会一直发事件到它对应的水缸里去
				}
			}
		}).subscribeOn(Schedulers.io());

		// 创建一个观察者
		final Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
			@Override
			public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
				for (int i = 0; i < 10; i++) {
					emitter.onNext("A");
				}
			}
		}).subscribeOn(Schedulers.io());

		Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
			@Override
			public String apply(final Integer integer, final String s) throws Exception {
				return integer + s;
			}
		}).observeOn(new ExecutorScheduler(Executors.newFixedThreadPool(3))).subscribe(new Consumer<String>() {
			@Override
			public void accept(final String s) throws Exception {
				System.out.println("正在执行 " + s);
			}
		}, new Consumer<Throwable>() {
			@Override
			public void accept(final Throwable throwable) throws Exception {
				System.out.println("异常： " + throwable);
			}
		});

		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (final InterruptedException e) {
			System.out.println("sleep Interrupted!");
		}
	}

}
