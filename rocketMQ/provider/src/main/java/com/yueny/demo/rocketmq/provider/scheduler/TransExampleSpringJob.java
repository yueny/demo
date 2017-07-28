package com.yueny.demo.rocketmq.provider.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.alibaba.rocketmq.common.message.Message;
import com.yueny.demo.rocketmq.provider.factory.NotifyMQProductFactory;
import com.yueny.demo.storage.mq.MqConstantsTest;
import com.yueny.demo.storage.mq.core.factory.product.helper.ProducerSendHelper;
import com.yueny.demo.storage.mq.data.JSONEvent;
import com.yueny.rapid.lang.json.JsonUtil;
import com.yueny.rapid.lang.util.StringUtil;
import com.yueny.rapid.lang.util.UuidUtil;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年4月18日 下午6:26:57
 *
 */
@Slf4j
@Service
public class TransExampleSpringJob {
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@Autowired
	private NotifyMQProductFactory transProducerFactory;

	/**
	 * 默认的执行方式是串行执行
	 *
	 * @Scheduled 注解用于标注这个方法是一个定时任务的方法，有多种配置可选。<br>
	 *            cron支持cron表达式，指定任务在特定时间执行；<br>
	 *            fixedRate以特定频率执行任务， 单位 毫秒milliseconds；<br>
	 *            fixedRateString以string的形式配置执行频率。
	 */
	@Scheduled(fixedRate = 30000)
	public void doSomething() {
		// 间隔30 * 1000 毫秒(0.5分钟),执行任务
		try {
			final String now = dateFormat.format(new Date());
			final Thread thread = Thread.currentThread();
			log.info("定时任务{}/{}，The time is now ：{}。", thread.getId(), thread.getName(), now);

			final String orderNo = UuidUtil.getUUIDForNumber24();
			final JSONEvent data = JSONEvent.builder().data(now).messageId(orderNo).build();

			final String json = JsonUtil.toJson(data);
			final Message msg = new Message(MqConstantsTest.Topic.MQ_DEMO_TOPIC_TEST.name(), // topic
					MqConstantsTest.TagsT.MQ_TRANS_TAG_MSG.name(), // tag
					(StringUtil.isEmpty(data.getMessageId()) ? data.getMessageId() : ""),
					json.getBytes(MqConstantsTest.DEFAULT_CHARSET_TYPE));

			ProducerSendHelper.send(transProducerFactory, msg, data);
		} catch (final Exception e) {
			log.error("【LogsExampleSpringJob任务】 超过超时，下次继续.");
		}
	}

}
