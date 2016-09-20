package com.yueny.demo.hystrix.service.demo;

import java.util.concurrent.TimeUnit;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;

/**
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月9日 下午8:55:42
 *
 */
public class HelloWorldFallbackCommand extends HystrixCommand<String> {
	public static void main(final String[] args) throws Exception {
		final HelloWorldFallbackCommand helloWorldCommand = new HelloWorldFallbackCommand("test-Fallback");
		final String s1 = helloWorldCommand.execute();
		// 运行结果: getFallback executed
		System.out.println(s1);

		/*
		 * 除了HystrixBadRequestException异常之外，所有从run()方法抛出的异常都算作失败，
		 * 并触发降级getFallback()和断路器逻辑。
		 * HystrixBadRequestException用在非法参数或非系统故障异常等不应触发回退逻辑的场景。
		 */
	}

	private final String name;

	public HelloWorldFallbackCommand(final String name) {
		super(
				/*
				 * 依赖分组:CommandGroup。命令分组用于对依赖操作分组,便于统计,汇总等...
				 * CommandGroup是每个命令最少配置的必选参数，在不指定ThreadPoolKey的情况下，
				 * 字面值用于对不同依赖的线程池/信号区分.
				 */
				Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("HelloWorldGroup"))
						/*
						 * 依赖命名:CommandKey。每个CommandKey代表一个依赖抽象,
						 * 相同的依赖要使用相同的CommandKey名称。
						 * 依赖隔离的根本就是对相同CommandKey的依赖做隔离.
						 */
						.andCommandKey(HystrixCommandKey.Factory.asKey("HelloWorld"))
						/*
						 * 线程池/信号:ThreadPoolKey。当对同一业务依赖做隔离时使用CommandGroup做区分,
						 * 但是对同一依赖的不同远程调用如(一个是redis
						 * 一个是http),可以使用HystrixThreadPoolKey做隔离区分.
						 * 最然在业务上都是相同的组，但是需要在资源上做隔离时，可以使用HystrixThreadPoolKey区分.
						 */
						.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("HelloWorldPool"))
						/* 配置依赖超时时间,500毫秒 */
						.andCommandPropertiesDefaults(
								HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(500)));
		this.name = name;
	}

	/**
	 * 使用Fallback() 提供降级策略<br>
	 * 重载HystrixCommand 的getFallback方法实现逻辑
	 */
	@Override
	protected String getFallback() {
		return "exeucute Falled";
	}

	@Override
	protected String run() throws Exception {
		// sleep 1 秒,调用会超时
		TimeUnit.MILLISECONDS.sleep(1000);

		return "Hello " + name + " thread:" + Thread.currentThread().getName();
	}

}
