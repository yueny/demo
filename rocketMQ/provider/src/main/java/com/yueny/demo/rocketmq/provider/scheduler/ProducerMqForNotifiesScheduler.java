package com.yueny.demo.rocketmq.provider.scheduler;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.yueny.demo.rocketmq.data.JSONEvent;
import com.yueny.demo.rocketmq.provider.message.IMessageNotifiesWorkflow;

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
			final StringBuilder sb = new StringBuilder();
			sb.append(i + 1);
			sb.append("总耗时:46201毫秒!");
			sb.append("总处理数目[平账/不平账/异常]:");
			sb.append(i);
			sb.append("[");
			sb.append(i);
			sb.append("/0/0]");
			sb.append("OVER");

			try {
				final JSONEvent data = new JSONEvent();
				data.setBody(sb.toString().getBytes(data.getCharset().charset()));

				try {
					messageNotifiesWorkflow.message(data);
				} catch (final Exception e) {
					e.printStackTrace();
				} finally {
					// .
				}
			} catch (final UnsupportedEncodingException e) {
				// TODO: handle exception
			}

		}
	}

}
