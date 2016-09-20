package com.yueny.demo.hystrix.service.demo.observer;

import java.util.concurrent.Future;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;

import rx.Observable;
import rx.Subscriber;
import rx.observables.BlockingObservable;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月9日 下午9:20:01
 *
 */
public class HelloWorldObservableCommand extends HystrixObservableCommand<String> {
	public static void main(final String[] args) throws Exception {
		final Observable<String> s = new HelloWorldObservableCommand("Bob").observe();
		final BlockingObservable<String> b = s.toBlocking();
		final Future<String> f = b.toFuture();

		System.out.println(f.get());
	}

	private final String name;

	protected HelloWorldObservableCommand(final String group) {
		// 指定命令组名(ExampleGroup)
		super(HystrixCommandGroupKey.Factory.asKey("HelloWorldGroup"));
		this.name = group;
	}

	@Override
	protected Observable<String> construct() {
		return Observable.create(new Observable.OnSubscribe<String>() {
			@Override
			public void call(final Subscriber<? super String> observer) {
				try {
					if (!observer.isUnsubscribed()) {
						// a real example would do work like a network call here
						observer.onNext("Hello");
						observer.onNext(name + "!");
						observer.onCompleted();
					}
				} catch (final Exception e) {
					observer.onError(e);
				}
			}
		});
	}

}
