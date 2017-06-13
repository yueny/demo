package com.yueny.demo.job.scheduler.job.spring;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yueny.demo.job.scheduler.springjob.DataBatchWithModifyForSpringJob;
import com.yueny.demo.job.service.BaseBizTest;

public class DemoDataBatchWithModifyJobTest extends BaseBizTest {
	@Autowired
	private DataBatchWithModifyForSpringJob demoDataBatchWithModifyJob;

	@Test
	public void testProcessData() {
		demoDataBatchWithModifyJob.processData();
	}

}
