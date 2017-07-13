package com.yueny.demo.rocketmq.consumer.strategy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.yueny.demo.rocketmq.MqConstants;
import com.yueny.demo.rocketmq.common.CounterHepler;
import com.yueny.demo.rocketmq.consumer.data.ScallorEvent;
import com.yueny.demo.rocketmq.core.factory.consumer.strategy.AbstractMqConsumer;
import com.yueny.demo.rocketmq.core.factory.consumer.strategy.IConsumer;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年7月12日 下午2:25:26
 *
 */
@Service
public class DemoTagMqConsumer extends AbstractMqConsumer<ScallorEvent> implements IConsumer<MqConstants.Tags> {
	/**
	 * 数据组装任务
	 */
	private class AssemblyEventDataTask implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					final ScallorEvent event = getQueue().take();

					try {
						System.out.println("完成消息处理: " + event.getMsgId() + "。" + Thread.currentThread().getName());
					} catch (final Exception e) {
						put(event);
						logger.error("监听消息异常，重新入池进行等待下次操作！", e);
					}
				} catch (final InterruptedException e) {
					e.printStackTrace();
				} catch (final Throwable e) { // 防御性容错
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 只支持一个线程的线程池，配置corePoolSize=maximumPoolSize=1，无界阻塞队列LinkedBlockingQueue；
	 * 保证任务由一个线程串行执行
	 */
	private final ExecutorService executorService = Executors.newSingleThreadExecutor();

	public DemoTagMqConsumer() {
		super();

		executorService.execute(new AssemblyEventDataTask());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.yueny.demo.rocketmq.core.factory.consumer.strategy.IConsumer#consumer
	 * (com.alibaba.rocketmq.common.message.MessageExt)
	 */
	@Override
	public ConsumeConcurrentlyStatus consumer(final MessageExt messageExt) {
		final ScallorEvent event = new ScallorEvent();
		event.setData(messageExt.getBody());
		event.setMsgId(messageExt.getMsgId());

		try {
			// 放入队列 put/add
			if (super.put(event)) {
				final long rl = CounterHepler.increment();
				System.out.println("Receive: " + rl + " New Messages: " + event.getMsgId());
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return ConsumeConcurrentlyStatus.RECONSUME_LATER;
	}

	@Override
	public MqConstants.Tags getCondition() {
		return MqConstants.Tags.DEMO_TAG_MQ_MSG;
	}

}
