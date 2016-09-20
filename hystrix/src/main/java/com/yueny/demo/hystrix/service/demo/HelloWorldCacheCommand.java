package com.yueny.demo.hystrix.service.demo;

import java.util.concurrent.TimeUnit;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月9日 下午8:55:42
 *
 */
public class HelloWorldCacheCommand extends HystrixCommand<String> {
	public static void main(final String[] args) throws Exception {
		// final HelloWorldCacheCommand helloWorldCommand = new
		// HelloWorldCacheCommand("test-Fallback");
		// final String s1 = helloWorldCommand.execute();
		// // 运行结果: getFallback executed
		// System.out.println(s1);

		HystrixRequestContext context = HystrixRequestContext.initializeContext();

		try {
			final HelloWorldCacheCommand command2a = new HelloWorldCacheCommand("2");
			final HelloWorldCacheCommand command2b = new HelloWorldCacheCommand("2");

			// Assert.assertTrue(command2a.execute());
			// isResponseFromCache判定是否是在缓存中获取结果
			// Assert.assertFalse(command2a.isResponseFromCache());
			// Assert.assertTrue(command2b.execute());
			// Assert.assertTrue(command2b.isResponseFromCache());
		} finally {
			context.shutdown();
		}
		context = HystrixRequestContext.initializeContext();

		try {
			final HelloWorldCacheCommand command3b = new HelloWorldCacheCommand("2");
			// Assert.assertTrue(command3b.execute());
			// Assert.assertFalse(command3b.isResponseFromCache());
		} finally {
			context.shutdown();
		}
	}

	private final String name;

	public HelloWorldCacheCommand(final String name) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("HelloWorldGroup"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("HelloWorld"))
				/* 配置依赖超时时间,500毫秒 */
				.andCommandPropertiesDefaults(
						HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(500)));
		this.name = name;
	}

	/**
	 * 请求缓存 Request-Cache<br>
	 * 重写getCacheKey方法,实现区分不同请求的逻辑
	 */
	@Override
	protected String getCacheKey() {
		return String.valueOf(name);
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
