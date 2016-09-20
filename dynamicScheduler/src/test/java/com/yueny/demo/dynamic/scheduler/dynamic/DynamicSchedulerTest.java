package com.yueny.demo.dynamic.scheduler.dynamic;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yueny.demo.dynamic.scheduler.BaseTest;
import com.yueny.demo.dynamic.scheduler.job.dynamic.DynamicJob;
import com.yueny.demo.dynamic.scheduler.job.dynamic.DynamicSchedulerFactory;
import com.yueny.demo.dynamic.scheduler.job.dynamic.JobParamManager;
import com.yueny.demo.dynamic.scheduler.job.dynamic.MonitoringInstanceJob;
import com.yueny.demo.dynamic.scheduler.util.HeartBeatFrequency;

/**
 * 管理动态Job的注册,删除
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月28日 下午9:50:25
 *
 */
public class DynamicSchedulerTest extends BaseTest {
	@Autowired
	private DynamicSchedulerFactory dynamicSchedulerFactory;

	@Test
	public void test() {
		System.out.println("dynamicSchedulerFactory is :" + dynamicSchedulerFactory);

		final String jobName = JobParamManager.generateMonitoringInstanceJobName("demo");

		final DynamicJob job = new DynamicJob(jobName).cronExpression(HeartBeatFrequency.FIVE.getCronExpression())
				.target(MonitoringInstanceJob.class).addJobData("key11", "1234567890");

		// try {
		// dynamicSchedulerFactory.registerJob(job);
		// } catch (final SchedulerException e) {
		// log.error("任务发布异常！", e);
		// }
		//
		// while (true) {
		// // .
		// }
	}

}
