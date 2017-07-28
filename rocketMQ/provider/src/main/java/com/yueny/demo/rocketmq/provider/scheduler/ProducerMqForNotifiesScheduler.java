package com.yueny.demo.rocketmq.provider.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.yueny.demo.rocketmq.provider.message.IMessageNotifiesWorkflow;
import com.yueny.demo.storage.mq.MqConstantsTest;
import com.yueny.demo.storage.mq.data.JSONEvent;
import com.yueny.rapid.lang.util.UuidUtil;

/**
 * Notifies
 *
 * @author <a href="mailto:yueny09@126.com"> 袁洋 2014年12月9日 下午4:52:34
 *
 * @category tag
 */
@Service
public class ProducerMqForNotifiesScheduler {
	@Autowired
	private IMessageNotifiesWorkflow messageNotifiesWorkflow;

	@Scheduled(cron = "0/12 * * * * ?")
	public void autoLogs() {
		for (int i = 0; i < 2; i++) {
			final String orderNo = UuidUtil.getUUIDForNumber24();
			final StringBuilder sb = new StringBuilder();
			sb.append("订单:");
			sb.append(orderNo);
			sb.append("总耗时:46201毫秒!");
			sb.append("总处理数目[平账/不平账/异常]:");
			sb.append(i);
			sb.append("[");
			sb.append(i);
			sb.append("/0/0]");
			sb.append("OVER");

			try {
				final JSONEvent data = JSONEvent.builder().data(sb.toString()).messageId(orderNo).build();

				messageNotifiesWorkflow.message(MqConstantsTest.Topic.MQ_DEMO_TOPIC_TEST,
						MqConstantsTest.TagsN.MQ_NOTIFIES_TAG_MSG, data);
			} catch (final Exception e) {
				e.printStackTrace();
			} finally {
				// .
			}

		}
	}

}
