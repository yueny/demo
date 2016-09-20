/**
 *
 */
package com.yueny.demo.rocketmq.consumer.message.counter;

import org.springframework.stereotype.Service;

import com.yueny.demo.common.monitor.counter.AbstractMonitorCounter;
import com.yueny.demo.rocketmq.consumer.enums.MessageComponentType;
import com.yueny.demo.rocketmq.consumer.message.IComponentType;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月1日 上午11:28:47
 *
 */
@Service
public class MessageMonitorCounter extends AbstractMonitorCounter implements IMessageCounter {
	private static final String[] ATTRIBUTES = { MessageComponentType.COUNTER_MESSAGE_ACCEPTED.node() };

	/**
	 * @param name
	 */
	public MessageMonitorCounter() {
		super("SourceMonitorCounter", ATTRIBUTES);
	}

	@Override
	public long addToReceivedCount(final IComponentType counterTag, final long delta) {
		// return addAndGet(counterTag.node(), delta);

		if (delta == 1L) {
			return this.incrementReceivedCount(counterTag);
		}
		return addAndGet(counterTag.node(), delta);
	}

	@Override
	public long getReceivedCount(final IComponentType counterTag) {
		return get(counterTag.node());
	}

	@Override
	public long incrementReceivedCount(final IComponentType counterTag) {
		return increment(counterTag.node());
	}
}
