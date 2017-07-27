package com.yueny.demo.job.scheduler.demo;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.yueny.demo.job.scheduler.elasticjob.simple.AbstractSimpleJob;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年4月18日 下午6:26:57
 *
 */
// @Component
public class EjExampleJob extends AbstractSimpleJob {
	@Override
	public void executor(final ShardingContext shardingContext) {
		System.out.println("EjExampleJob");
	}
}
