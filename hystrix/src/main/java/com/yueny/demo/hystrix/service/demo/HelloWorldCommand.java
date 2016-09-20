package com.yueny.demo.hystrix.service.demo;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

/**
 * run方法中实现的是业务逻辑，由Hystrix负责调用
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月9日 下午8:55:42
 *
 */
public class HelloWorldCommand extends HystrixCommand<String> {
	public static void main(final String[] args) throws Exception {
		// 每个Command对象只能调用一次,不可以重复调用,
		// 重复调用对应异常信息:This instance can only be executed once. Please
		// instantiate a new instance.

		/* 同步调用使用command.execute() ,效果等同于 command.queue().get(); */
		HelloWorldCommand helloWorldCommand = new HelloWorldCommand("Synchronous-hystrix");
		final String s1 = helloWorldCommand.execute();
		// 运行结果: Hello Synchronous-hystrix thread:hystrix-ExampleGroup-1
		System.out.println(s1);

		/*
		 * 异步调用,可自由控制获取结果时机:异步调用使用 command.queue().get(timeout,
		 * TimeUnit.MILLISECONDS);
		 */
		helloWorldCommand = new HelloWorldCommand("Asynchronous-hystrix");
		final Future<String> future = helloWorldCommand.queue();
		// get操作不能超过command定义的超时时间,默认:1秒
		// 运行结果: Hello Asynchronous-hystrix thread:hystrix-ExampleGroup-2
		System.out.println(future.get(100, TimeUnit.MILLISECONDS));

		/* 注册异步事件回调执行 */
		final Observable<String> fs = new HelloWorldCommand("Bob").observe();
		// 注册结果回调事件
		fs.subscribe(new Action1<String>() {
			@Override
			public void call(final String result) {
				// 执行结果处理,result 为HelloWorldCommand返回的结果
				// 用户对结果做二次处理.
				System.out.println(result);
			}
		});
		// 注册完整执行生命周期事件
		fs.subscribe(new Observer<String>() {
			@Override
			public void onCompleted() {
				// onNext/onError完成之后最后回调
				System.out.println("execute onCompleted");
			}

			@Override
			public void onError(final Throwable e) {
				// 当产生异常时回调
				System.out.println("onError " + e.getMessage());
				e.printStackTrace();
			}

			@Override
			public void onNext(final String v) {
				// 获取结果后回调
				System.out.println("onNext: " + v);
			}
		});
		// 运行结果:
		// Hello Bob thread:hystrix-ExampleGroup-3
		// onNext: Hello Bob thread:hystrix-ExampleGroup-3
		// execute onCompleted
	}

	private final String name;

	protected HelloWorldCommand(final String group) {
		// 最少配置:指定命令组名(CommandGroup)
		super(HystrixCommandGroupKey.Factory.asKey("HelloWorldGroup"));
		this.name = group;
	}

	@Override
	protected String run() throws Exception {
		// 依赖逻辑封装在run()方法中
		return "Hello " + name + " thread:" + Thread.currentThread().getName();
	}

}
