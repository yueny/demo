package com.yueny.demo.micros;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.common.util.concurrent.AbstractIdleService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年11月16日 上午9:48:39
 *
 */
@Slf4j
public class AgentApplication extends AbstractIdleService {

	public static void main(final String[] args) throws IOException {
		final AgentApplication bootstrap = new AgentApplication();
		bootstrap.startAsync();
		log.info("start...");

		try {
			final Object lock = new Object();
			synchronized (lock) {
				while (true) {
					lock.wait();
				}
			}
		} catch (final InterruptedException ex) {
			System.err.println(ex);
			log.error("", ex);
		}
	}

	private ClassPathXmlApplicationContext context;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.google.common.util.concurrent.AbstractIdleService#shutDown()
	 */
	@Override
	protected void shutDown() throws Exception {
		// Stop the service.
		context.stop();

		log.info("-------------service stoppedsuccessfully-------------");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.google.common.util.concurrent.AbstractIdleService#startUp()
	 */
	@Override
	protected void startUp() throws Exception {
		// Start the service.
		log.info("Start the service...");

		try {
			context = new ClassPathXmlApplicationContext("classpath:config/all.xml");

			context.start();
		} catch (final Exception e) {
			e.printStackTrace();
			log.error("", e);
		}

		// context.registerShutdownHook();
		log.info("----------------provider service startedsuccessfully------------");
	}

}
