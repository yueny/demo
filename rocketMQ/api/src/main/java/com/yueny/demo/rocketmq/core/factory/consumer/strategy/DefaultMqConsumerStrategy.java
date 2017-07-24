package com.yueny.demo.rocketmq.core.factory.consumer.strategy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.yueny.demo.rocketmq.MqConstants;
import com.yueny.demo.rocketmq.common.CounterHepler;
import com.yueny.demo.rocketmq.data.JSONEvent;
import com.yueny.rapid.lang.json.JsonUtil;

/**
 * 默认处理策略
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年7月12日 下午2:25:26
 *
 */
public class DefaultMqConsumerStrategy extends AbstractMqConsumerStrategy<JSONEvent> {
	/**
	 * 数据组装任务
	 */
	private class AssemblyEventDataTask implements Runnable {
		public void run() {
			while (true) {
				try {
					final JSONEvent event = getQueue().take();

					try {
						System.out.println("完成JSON DATA消息处理: " + event + "。" + Thread.currentThread().getName());
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

	public DefaultMqConsumerStrategy() {
		super();

		executorService.execute(new AssemblyEventDataTask());
	}

	public ConsumeConcurrentlyStatus consumer(final MessageExt messageExt) {
		// body and eventJson is JSONEvent
		final String eventJson = new String(messageExt.getBody(), MqConstants.DEFAULT_CHARSET_TYPE);
		final JSONEvent event = JsonUtil.fromJson(eventJson, JSONEvent.class);

		// RocketMQ不保证消息不重复，如果你的业务需要保证严格的不重复消息，需要你自己在业务端去重。
		// 消费端处理消息的业务逻辑保持幂等性
		// 保证每条消息都有唯一编号且保证消息处理成功与去重表的日志同时出现

		try {
			// 放入队列 put/add
			if (super.put(event)) {
				final long rl = CounterHepler.increment();

				logger.info("{} --> Receive MessagesID[{}/{}]:{}.", rl, messageExt.getMsgId(), event.getMessageId());
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return ConsumeConcurrentlyStatus.RECONSUME_LATER;
	}

}
