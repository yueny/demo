/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package com.yueny.demo.job.scheduler.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dangdang.ddframe.job.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

/**
 * 代替
 * 
 * <pre>
 * <reg:zookeeper id="regCenter" server-lists="${serverLists}" namespace="${namespace}" base-sleep-time-milliseconds="${baseSleepTimeMilliseconds}" max-sleep-time-milliseconds="${maxSleepTimeMilliseconds}" max-retries="${maxRetries}" />
 * </pre>
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年6月20日 上午10:22:41
 *
 */
@Configuration
@ConditionalOnExpression("'${regCenter.server.lists}'.length() > 0")
public class RegistryCenterConfig {
	@Value("${regCenter.base.sleep.time.milliseconds}")
	private int baseSleepTimeMilliseconds;
	@Value("${regCenter.max.retries}")
	private int maxRetries;
	@Value("${regCenter.max.sleep.time.milliseconds}")
	private int maxSleepTimeMilliseconds;

	/**
	 * 实现：<br>
	 * <reg:zookeeper id="regCenter" server-lists="${ej.server.lists}" namespace
	 * ="${ej.namespace}" base-sleep-time-milliseconds=
	 * "${ej.base.sleep.time.milliseconds}" max-sleep-time-milliseconds=
	 * "${ej.maxSleepTimeMilliseconds}" max-retries="${ej.maxRetries}"/>
	 *
	 * @param serverList
	 * @param namespace
	 * @return
	 */
	@Bean(initMethod = "init")
	public CoordinatorRegistryCenter regCenter(@Value("${regCenter.server.lists}") final String serverList,
			@Value("${regCenter.namespace}") final String namespace) {
		final ZookeeperConfiguration zkc = new ZookeeperConfiguration(serverList, namespace);
		zkc.setBaseSleepTimeMilliseconds(baseSleepTimeMilliseconds);
		zkc.setMaxSleepTimeMilliseconds(maxSleepTimeMilliseconds);
		zkc.setMaxRetries(maxRetries);

		final CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(zkc);
		// 初始化 ，由 @Bean(initMethod = "init") 完成
		// regCenter.init();

		return regCenter;
	}

}
