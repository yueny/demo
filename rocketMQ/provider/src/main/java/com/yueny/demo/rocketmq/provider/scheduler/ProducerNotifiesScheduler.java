package com.yueny.demo.rocketmq.provider.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.yueny.demo.rocketmq.provider.message.IMessageNotifiesWorkflow;

/**
 * Notifies
 * 
 * @author <a href="mailto:yueny09@126.com"> 袁洋 2014年12月9日 下午4:52:34
 *
 * @category tag
 */
// @Service
public class ProducerNotifiesScheduler {
	@Autowired
	private IMessageNotifiesWorkflow notifiesMessageWorkflow;

	@Scheduled(cron = "0/10 * * * * ?")
	public void autoLogs() {
		for (int i = 0; i < 2; i++) {
			try {
				notifiesMessageWorkflow.message(i, 1);
			} catch (final Exception e) {
				e.printStackTrace();
			} finally {
				// .
			}
		}
	}

}
