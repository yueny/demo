package com.yueny.demo.rocketmq.core.factory.consumer.strategy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.yueny.demo.rocketmq.common.CounterHepler;
import com.yueny.demo.rocketmq.data.JSONEvent;
import com.yueny.demo.rocketmq.enums.HeaderType;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年7月12日 下午2:25:26
 *
 */
public class DefaultMqConsumer extends AbstractMqConsumer<JSONEvent> {
	/**
	 * 数据组装任务
	 */
	private class AssemblyEventDataTask implements Runnable {
		public void run() {
			while (true) {
				try {
					final JSONEvent event = getQueue().take();

					try {
						System.out.println("完成消息处理: " + event.getHeaders().get(HeaderType.MSG_ID) + "。"
								+ Thread.currentThread().getName());
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

	public DefaultMqConsumer() {
		super();

		executorService.execute(new AssemblyEventDataTask());
	}

	public ConsumeConcurrentlyStatus consumer(final MessageExt messageExt) {
		final JSONEvent event = new JSONEvent();
		event.setBody(messageExt.getBody());
		event.addHeaders(HeaderType.MSG_ID, messageExt.getMsgId());

		try {
			// 放入队列 put/add
			if (super.put(event)) {
				final long rl = CounterHepler.increment();
				System.out.println("Receive: " + rl + " New Messages: " + messageExt.getMsgId());
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return ConsumeConcurrentlyStatus.RECONSUME_LATER;
	}

}
