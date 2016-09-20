package com.yueny.demo.hystrix.service.mq.impl;

import java.util.concurrent.BlockingQueue;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.yueny.demo.common.model.Event;
import com.yueny.demo.hystrix.service.IDataPrecipitationService;
import com.yueny.demo.hystrix.service.mq.IMessageHandler;
import com.yueny.demo.hystrix.service.mq.counter.IMessageCounter;
import com.yueny.demo.hystrix.service.mq.factory.ConsumerFactory;

import lombok.Getter;

public abstract class BaseMessageHandler implements IMessageHandler, InitializingBean {
	@Autowired
	@Getter
	private IMessageCounter counter;
	@Autowired
	protected IDataPrecipitationService dataPrecipitationService;
	@Autowired
	protected ConsumerFactory defaultConsumerFactory;

	@Override
	public void afterPropertiesSet() throws Exception {
		init();

		exectue();
	}

	public abstract BlockingQueue<Event> getBlockingQueue();

	public abstract void init();

}
