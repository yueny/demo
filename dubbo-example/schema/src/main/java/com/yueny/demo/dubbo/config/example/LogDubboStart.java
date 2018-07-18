package com.yueny.demo.dubbo.config.example;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.common.util.concurrent.AbstractIdleService;
import com.yueny.demo.dubbo.config.api.LockConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogDubboStart extends AbstractIdleService {
	public static void main(final String[] args) throws IOException {
		final LogDubboStart bootstrap = new LogDubboStart();
		bootstrap.startAsync();

		try {
			final Object lock = new Object();
			synchronized (lock) {
				while (true) {
					lock.wait();
				}
			}
		} catch (final InterruptedException ex) {
			log.error("", ex);
			System.err.println(ex);
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
		try {
			context = new ClassPathXmlApplicationContext("classpath:example/config/all.xml");

			final LockConfig p = (LockConfig) context.getBean("lockYou");
			System.out.println(p.getId());
			System.out.println(p.getName());
			System.out.println(p.getGroup());
			System.out.println(p.getKey());

			context.start();
		} catch (final Exception e) {
			e.printStackTrace();
		}

		context.registerShutdownHook();
		log.info("----------------provider service startedsuccessfully------------");
	}
}
