package com.yueny.demo.frequence.refuse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

import lombok.extern.slf4j.Slf4j;

/**
 * 限制调用频率,达到频率就拒绝
 * <p>
 * ThreadSafe
 * </p>
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月14日 下午11:25:18
 *
 */
@Slf4j
public class FrequenceRefuseUtils {
	private static Map<String, ThreadLocal<FrequenceSemUnit>> threadLocal = new ConcurrentHashMap<>();

	/**
	 * <p>
	 * Limit call count in split time
	 * </p>
	 *
	 * @param url
	 *            限制的url
	 * @param limitSplitTime
	 * @param limitCount
	 *            在指定限频时间内执行响应数量
	 */
	public static boolean refuse(final String url, final long limitSplitTime, final int limitCount)
			throws InterruptedException {
		if (!threadLocal.containsKey(url)) {
			threadLocal.put(url, new ThreadLocal<FrequenceSemUnit>() {
				@Override
				protected synchronized FrequenceSemUnit initialValue() {
					final FrequenceSemUnit funit = new FrequenceSemUnit(limitSplitTime, limitCount);
					return funit;
				}
			});
		}

		final FrequenceSemUnit funit = threadLocal.get(url).get();
		final Semaphore sem = funit.getSem();

		// if (funit.getRealCount() >= funit.getLimitCount()) {
		if (sem.tryAcquire()) {
			// 获得许可
			sem.acquire();
			funit.plusRealCount();
			return false;
		}

		log.warn("操作过于频繁,限流信息:{}, 请求拒绝!", funit);
		return true;
	}

}
