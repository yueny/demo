/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yueny.demo.frequence.block;

import lombok.extern.slf4j.Slf4j;

/**
 * 限制调用频率,达到频率就将请求阻塞
 * <p>
 * ThreadSafe
 * </p>
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月14日 下午7:49:19
 *
 */
@Slf4j
public final class FrequenceBlockUtils {

	private static ThreadLocal<FrequenceBlockUnit> threadLocal = new ThreadLocal<FrequenceBlockUnit>() {
		@Override
		protected synchronized FrequenceBlockUnit initialValue() {
			final FrequenceBlockUnit funit = new FrequenceBlockUnit();
			funit.getWatch().start();

			return funit;
		}
	};

	/**
	 * <p>
	 * Limit call count in split time
	 * </p>
	 *
	 * @param limitSplitTime
	 * @param limitCount
	 *            在指定限频时间内执行响应数量
	 */
	public static boolean limit(final long limitSplitTime, final int limitCount) throws InterruptedException {
		final FrequenceBlockUnit funit = threadLocal.get();
		funit.setLimitSplitTime(limitSplitTime);
		funit.setLimitCount(limitCount);

		funit.getWatch().split();

		// 差异时间
		final long diffTime = funit.getLimitSplitTime() - funit.getWatch().getSplitTime();
		if (diffTime > 0) {
			if (funit.getRealCount() >= funit.getLimitCount()) {
				funit.getWatch().suspend();

				log.warn("操作过于频繁,限流信息:{}, 请求阻塞!剩余响应时间:{}.", funit, diffTime);
				Thread.sleep(diffTime);
				funit.getWatch().resume();
				funit.zeroRealCount();
			}
		}

		funit.plusRealCount();
		return false;
	}

}
