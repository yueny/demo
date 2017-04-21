package com.yueny.demo.job.scheduler.job.spring;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.yueny.demo.job.service.BaseBizTest;
import com.yueny.demo.micros.scheduler.job.example.ExecutorExampleJob;

public class ExecutorExampleJobTest extends BaseBizTest {
	private final ExecutorExampleJob example = new ExecutorExampleJob();

	@Test
	public void testInit() {
		example.init();

		while (true) {
			try {
				TimeUnit.MILLISECONDS.sleep(2000);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
