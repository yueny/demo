package com.yueny.demo.rocketmq.provider.scheduler;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;
import com.yueny.demo.common.constant.MqConstants;
import com.yueny.demo.rocketmq.data.EventData;
import com.yueny.demo.rocketmq.provider.factory.ProducerFactory;
import com.yueny.rapid.lang.json.JsonUtil;

/**
 * demo
 *
 * @author <a href="mailto:yueny09@126.com"> 袁洋 2014年12月9日 下午4:52:34
 *
 * @category tag
 */
@Service
public class ProducerDemoScheduler {
	/**
	 * 发送失败数量
	 */
	private final AtomicLong failCounter = new AtomicLong(0L);
	@Autowired
	private ProducerFactory producerFactory;
	/**
	 * 发送成功数量
	 */
	private final AtomicLong successCounter = new AtomicLong(0L);

	@Scheduled(cron = "0/10 * * * * ?")
	public void autoLogs() {
		for (int i = 0; i < 1; i++) {
			try {
				sendDemoMsg(i, 1);
			} catch (final Exception e) {
				e.printStackTrace();
			} finally {
				// .
			}
		}
	}

	private void sendDemoMsg(final int trys, final int steps) throws MQClientException {
		System.out.println("--开始第" + trys + "次消息批量推送");

		final DefaultMQProducer producer = producerFactory.getProducer();

		/**
		 * 下面这段代码表明一个Producer对象可以发送多个topic，多个tag的消息。
		 * 注意：send方法是同步调用，只要不抛异常就标识成功。但是发送成功也可会有多种状态，<br>
		 * 例如消息写入Master成功，但是Slave不成功，这种情况消息属于成功，但是对于个别应用如果对消息可靠性要求极高，<br>
		 * 需要对这种情况做处理。另外，消息可能会存在发送失败的情况，失败重试由应用来处理。
		 */
		for (int i = 0; i < steps; i++) {
			try {
				final StringBuilder sb = new StringBuilder();
				sb.append(trys);
				sb.append("/");
				sb.append(i);
				sb.append("--");
				sb.append("Just for test.push!");

				final EventData data = new EventData();
				data.setContent(sb.toString());
				data.setSubject("消息");
				data.setEventRequestType("TEST_2");

				final String json = JsonUtil.toJson(data);
				final Message msg = new Message(MqConstants.Topic.DEMO_MQ_TOPIC.topic(), // topic
						MqConstants.Tags.DEMO_TAG_MQ_MSG.tag(), // tag
						json.getBytes());

				final SendResult sendResult = producer.send(msg);
				if (sendResult == null || sendResult.getSendStatus() != SendStatus.SEND_OK) {
					System.out.println("消息:" + json);
					System.out.println("消息通知失败,result:" + sendResult);
					failCounter.incrementAndGet();
				} else {
					System.out.println("[S]消息:" + json);
					successCounter.incrementAndGet();
				}
			} catch (final Exception e) {
				e.printStackTrace();
				failCounter.incrementAndGet();
			}
		}
		System.out.println("--第" + trys + "次消息批量推送结束, 已成功/失败发送总数量:" + successCounter.get() + "/" + failCounter.get());
	}

}
