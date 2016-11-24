package com.yueny.demo.job.scheduler.job.spring;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yueny.demo.job.scheduler.job.spring.SpringDataBatchWithModifyJob;
import com.yueny.demo.job.service.BaseBizTest;

public class DemoDataBatchWithModifyJobTest extends BaseBizTest {
	@Autowired
	private SpringDataBatchWithModifyJob demoDataBatchWithModifyJob;

	@Test
	public void testProcessData() {
		demoDataBatchWithModifyJob.processData();
	}

}
