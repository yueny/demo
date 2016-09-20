package com.yueny.demo.hystrix.service.mq.counter;

public interface IMessageCounter {
	/**
	 * @param delta
	 * @return
	 */
	long addCount(String counterTag, long delta);

	/**
	 * @return 消息接受数目
	 */
	long getCount(String counterTag);

	/**
	 * @return 消息接受数目增加
	 */
	long incrementCount(String counterTag);
	// long decrementCount(final String counter)
}
