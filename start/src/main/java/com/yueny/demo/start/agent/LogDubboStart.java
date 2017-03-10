package com.yueny.demo.start.agent;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.common.util.concurrent.AbstractIdleService;

public class LogDubboStart extends AbstractIdleService {
	public static LogDubboStart bootstrap = null;

	private static Logger logger = LoggerFactory.getLogger(LogDubboStart.class);

	public static void main(final String[] args) throws IOException {
		if (args.length != 1) {
			logger.info("Please specify operation:");
			logger.info(" start - start LogDubboStart command recieiver");
			logger.info(" shutdown - shutdown LogDubboStart command recieiver");
			System.exit(0);
		} else {
			final String opera = args[0];
			if (StringUtils.equalsIgnoreCase("start", opera)) {
				goStart();
			} else if (StringUtils.equalsIgnoreCase("shutdown", opera)) {
				goStop();
			}
		}
	}

	private static void goStart() {
		try {
			bootstrap = new LogDubboStart();
			bootstrap.startAsync();

			try {
				final Object lock = new Object();
				synchronized (lock) {
					while (true) {
						lock.wait();
					}
				}
			} catch (final InterruptedException ex) {
				System.err.println(ex);
			}
		} catch (final Exception e) {
			logger.info("agent started already on this machine with same portno;", e);
			System.exit(0);
		}
	}

	private static void goStop() {
		if (bootstrap != null) {
			bootstrap.stopAsync();
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

		System.out.println("-------------service stoppedsuccessfully-------------");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.google.common.util.concurrent.AbstractIdleService#startUp()
	 */
	@Override
	protected void startUp() throws Exception {
		try {
			context = new ClassPathXmlApplicationContext("classpath:/config/all.xml");
			context.start();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		context.registerShutdownHook();

		System.out.println("----------------provider service startedsuccessfully------------");
	}

}
