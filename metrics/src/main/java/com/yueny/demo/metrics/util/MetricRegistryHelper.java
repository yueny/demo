package com.yueny.demo.metrics.util;

import static com.codahale.metrics.MetricRegistry.name;

import org.springframework.beans.factory.annotation.Autowired;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年10月27日 上午11:38:41
 *
 */
public class MetricRegistryHelper {
	/**
	 * 中心部件 MetricRegistry。 它是程序中所有度量metric的容器。
	 */
	@Autowired
	private MetricRegistry metricRegistry;

	/**
	 * histogram
	 */
	public Histogram histogram(final String name) {
		final String namer = name("histogram", name);
		// namer相同, 获取的为同一对象, 均为单例
		return metricRegistry.histogram(namer);
	}

	/**
	 * Meter用来计算事件的速率<br>
	 * 事件总数，平均速率,包含1分钟，5分钟，15分钟的速率
	 */
	public Meter meter(final String name) {
		final String namer = name("meter", name);
		// namer相同, 获取的为同一对象, 均为单例
		return metricRegistry.meter(namer);
	}

	/**
	 * Timer用来测量一段代码被调用的速率和用时<br>
	 * 如统计QPS
	 */
	public Timer timer(final String name) {
		final String timeName = name("calculation-duration", name);

		return metricRegistry.timer(timeName);
	}

}
