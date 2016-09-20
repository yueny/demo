/**
 *
 */
package com.yueny.demo.hystrix.service.mq.counter;

import org.springframework.stereotype.Service;

import com.yueny.demo.common.monitor.counter.AbstractMonitorCounter;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月1日 上午11:28:47
 *
 */
@Service
public class MessageMonitorCounter extends AbstractMonitorCounter implements IMessageCounter {
	/**
	 * @param name
	 */
	public MessageMonitorCounter() {
		super("SourceMonitorCounter");
	}

	@Override
	public long addCount(final String counterTag, final long delta) {
		// return super.addAndGet(counterTag.node(), delta);

		if (delta == 1L) {
			return this.incrementCount(counterTag);
		}
		return super.addAndGet(counterTag, delta);
	}

	@Override
	public long getCount(final String counterTag) {
		return super.get(counterTag);
	}

	@Override
	public long incrementCount(final String counterTag) {
		return super.increment(counterTag);
	}

}
