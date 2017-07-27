package com.yueny.demo.storage.mq.core.factory.product.helper;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yueny.demo.storage.mq.core.factory.product.helper.asyn.IAsynExecter;

/**
 * 发送失败数据搜集器
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年3月13日 下午2:04:10
 *
 */
public class SendFailRetrysAsynExecter implements Runnable, IAsynExecter {
	private static Logger logger = LoggerFactory.getLogger(SendFailRetrysAsynExecter.class);
	private final Long interval = 2000L;
	private volatile boolean isRunning = true;
	private final LinkedBlockingQueue<ProducerSendModel> sendFailQueue;

	public SendFailRetrysAsynExecter() {
		this.sendFailQueue = SendFailQueueFactory.getQueueByDefault();
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		logger.debug("SendFailRetrys enter run method ......");
		while (isRunning) {
			if (sendFailQueue.isEmpty()) {
				try {
					TimeUnit.MILLISECONDS.sleep(interval);
				} catch (final InterruptedException e) {
					logger.error("sleep interrupted !", e);
				}
			}
			try {
				final ProducerSendModel element = sendFailQueue.poll(interval, TimeUnit.MILLISECONDS);
				if (element != null) {
					logger.debug("put data into data queue again," + element);
					ProducerSendHelper.send(element.getProducerFactory(), element.getMessage());
				}
			} catch (final InterruptedException e) {
				logger.error("poll element from queue error", e);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yueny.demo.rocketmq.core.factory.product.helper.asyn.IEstablishAsyn#
	 * shutdown()
	 */
	public void shutdown() {
		logger.info("SendFailCollector关闭。。。。");
		this.isRunning = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.yueny.demo.rocketmq.core.factory.product.helper.asyn.IEstablishAsyn#
	 * startup()
	 */
	public void startup() {
		logger.info("对应SendFailCollector启动。。。。");
		this.isRunning = true;
	}

}
