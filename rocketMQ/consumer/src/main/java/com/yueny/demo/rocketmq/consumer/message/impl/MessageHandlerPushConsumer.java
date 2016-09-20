package com.yueny.demo.rocketmq.consumer.message.impl;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.google.common.collect.Lists;
import com.yueny.demo.common.concurrent.NamedThreadFactory;
import com.yueny.demo.common.constant.MqConstants;
import com.yueny.demo.common.enums.HeaderType;
import com.yueny.demo.common.model.Event;
import com.yueny.demo.common.model.JSONEvent;
import com.yueny.demo.common.util.DigstUtil;
import com.yueny.demo.rocketmq.consumer.enums.MessageComponentType;
import com.yueny.demo.rocketmq.consumer.factory.ConsumerFactory;
import com.yueny.demo.rocketmq.consumer.message.IComponentType;
import com.yueny.demo.rocketmq.consumer.message.IMessageHandler;
import com.yueny.demo.rocketmq.consumer.message.counter.IMessageCounter;

import lombok.Getter;

/**
 * 普通的 消息订阅
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年3月13日 下午3:15:14
 *
 */
@Service
public class MessageHandlerPushConsumer implements IMessageHandler, InitializingBean {
	/**
	 * 数据组装任务
	 */
	private class AssemblyEventDataTask implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					final Event event = getBlockingQueue().take();

					System.out.println("完成消息处理: " + event.getHeaders().get(HeaderType.MSG_ID));
				} catch (final InterruptedException e) {
					e.printStackTrace();
				} catch (final Throwable e) { // 防御性容错
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 阻塞队列大小, 默认2000
	 */
	protected static final int DEFAULT_CAPACITY = 2000;
	// 定时器
	private final ScheduledFuture<?> channelFuture;
	@Autowired
	private ConsumerFactory consumerFactory;
	@Autowired
	@Getter
	private IMessageCounter counter;
	/**
	 * 只支持一个线程的线程池，配置corePoolSize=maximumPoolSize=1，无界阻塞队列LinkedBlockingQueue；
	 * 保证任务由一个线程串行执行
	 */
	private final ExecutorService executorService = Executors.newSingleThreadExecutor();
	/**
	 * MQ数据源内部维护的阻塞队列<br>
	 * 数据消费通过 Channel 操纵, MQ本身内部只负责堆积
	 */
	private final BlockingQueue<Event> queue;

	/**
	 * 定时任务执行器
	 */
	private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1,
			new NamedThreadFactory("channelMonitorTimer", true));

	/**
	 * 阻塞队列大小, 默认2000
	 */
	protected int capacity = DEFAULT_CAPACITY;

	public MessageHandlerPushConsumer() {
		// 阻塞队列,先进先出, 每次操作的是队列头
		queue = new ArrayBlockingQueue<Event>(capacity);

		channelFuture = scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					final int used = queue.size();

					final StringBuilder sb = new StringBuilder();
					sb.append("ch------>");
					sb.append(getCounter().getReceivedCount(counterTag()));
					sb.append("|");
					sb.append(DigstUtil.pertDigst(used, capacity));

					System.out.println(sb.toString());
				} catch (final Throwable t) { // 防御性容错
					System.out.println("Unexpected error occur at channel monitor, cause:" + t.getMessage());
				}
			}
		}, 1000L, 1000L, TimeUnit.MILLISECONDS);

		executorService.execute(new AssemblyEventDataTask());
	}

	/**
	 * 如果队列是满的话，报错
	 *
	 * @param event
	 */
	public boolean add(final Event event) {
		return add(Arrays.asList(event));
	}

	/**
	 * 如果队列是满的话，报错
	 *
	 * @param events
	 */
	public boolean add(final List<Event> events) {
		try {
			return queue.addAll(events);
		} catch (final IllegalStateException e) {
			e.printStackTrace();
		} catch (final Throwable e) { // 防御性容错
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		exectue();
	}

	public boolean consumeMsg(final List<MessageExt> msgs) {
		final List<Event> events = Lists.newArrayList();

		for (final MessageExt msg : msgs) {
			final Event event = new JSONEvent();
			event.setBody(msg.getBody());
			event.addHeaders(HeaderType.MSG_ID, msg.getMsgId());

			// System.out.println(new String(msg.getBody()) + "。" +
			// Thread.currentThread().getName()
			// + " Receive New Messages: " + msg.toString());
			events.add(event);
		}

		try {
			// System.out.println("-开始MQ消费消息: " + events.size() + " 条。");

			// 放入队列 put/add
			return put(events);
		} catch (final IllegalStateException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean exectue() {
		try {
			final DefaultMQPushConsumer consumer = consumerFactory.getPushConsumer();

			subscribe(consumer);

			consumer.registerMessageListener(new MessageListenerConcurrently() {
				/**
				 * 默认msgs里只有一条消息，可以通过设置consumeMessageBatchMaxSize参数来批量接收消息
				 */
				@Override
				public ConsumeConcurrentlyStatus consumeMessage(final List<MessageExt> msgs,
						final ConsumeConcurrentlyContext context) {
					try {
						if (consumeMsg(msgs)) {
							getCounter().addToReceivedCount(counterTag(), msgs.size());
							return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
						}
					} catch (final Exception e) {
						e.printStackTrace();
					}
					return ConsumeConcurrentlyStatus.RECONSUME_LATER;
				}
			});

			/**
			 * Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
			 */
			consumer.start();
			System.out.println("Consumer Started.");
			return true;
		} catch (final MQClientException e) {
			e.printStackTrace();
		}
		return false;
	}

	public BlockingQueue<Event> getBlockingQueue() {
		return queue;
	}

	/**
	 * 将指定元素添加到此队列中,如果队列是满的话，会阻塞当前线程
	 *
	 * @param event
	 */
	public boolean put(final Event event) {
		try {
			queue.put(event);
			return true;
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 如果队列是满的话，会阻塞当前线程
	 *
	 * @param events
	 */
	public boolean put(final List<Event> events) {
		for (final Event event : events) {
			if (!put(event)) {
				return false;
			}
		}
		return true;
	}

	public void subscribe(final DefaultMQPushConsumer consumer) throws MQClientException {
		// 订阅指定uranus下tags等于purchase
		consumer.subscribe(MqConstants.Topic.NOTIFIES_MQ_TOPIC.topic(), // topic
				MqConstants.Tags.NOTIFIES_TAG_WARNING.tag()// tag
		);
		consumer.setConsumeMessageBatchMaxSize(2);
	}

	protected IComponentType counterTag() {
		return MessageComponentType.COUNTER_MESSAGE_ACCEPTED;
	}

}
