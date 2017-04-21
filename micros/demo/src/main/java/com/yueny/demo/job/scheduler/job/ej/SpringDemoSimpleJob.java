package com.yueny.demo.job.scheduler.job.ej;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.yueny.demo.job.service.IDataPrecipitationService;

//@Component
public class SpringDemoSimpleJob implements SimpleJob {
	@Autowired
	private IDataPrecipitationService dataPrecipitationService;

	// List<String> result = Splitter.on("-").trimResults().splitToList(input);
	private final String shardingItemParameters = "0=A,1=B,2=C,3=D,4=E,5=F,6=G,7=H,8=I,9=J";
	private final int shardingTotalCount = 10;

	@Override
	public void execute(final ShardingContext shardingContext) {
		System.out.println(String.format("------Thread ID: %s, Date: %s, Sharding Context: %s, Action: %s",
				Thread.currentThread().getId(), new Date(), shardingContext, "simple job"));

		// dataPrecipitationService.findById(shardingContext.getShardingItem());
	}
}
