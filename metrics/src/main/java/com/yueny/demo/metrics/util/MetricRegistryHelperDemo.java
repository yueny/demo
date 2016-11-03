package com.yueny.demo.metrics.util;

import static com.codahale.metrics.MetricRegistry.name;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ScheduledReporter;
import com.codahale.metrics.Timer;

import lombok.Getter;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年10月27日 上午11:38:41
 *
 */
public class MetricRegistryHelperDemo {
	/**
	 * @author yueny09 <deep_blue_yang@163.com>
	 *
	 * @DATE 2016年10月27日 上午11:38:44
	 *
	 */
	public static class InnerCaller {
		@Getter
		private final MetricRegistry reg;

		private InnerCaller(final MetricRegistry reg) {
			this.reg = reg;
		}

		/**
		 * Meter用来计算事件的速率<br>
		 * 事件总数，平均速率,包含1分钟，5分钟，15分钟的速率
		 */
		public Meter meter(final String name) {
			final String namer = requestName(name);
			if (REQUESTS_METER_CACHE.containsKey(namer)) {
				return REQUESTS_METER_CACHE.get(namer);
			}

			final Meter requests = metricsReg.meter(namer);
			REQUESTS_METER_CACHE.put(namer, requests);
			return requests;
		}

		/**
		 * metrics 计数一次
		 */
		public void meterMark(final String name) {
			REQUESTS_METER_CACHE.get(requestName(name)).mark();
		}

		/**
		 * Timer用来测量一段代码被调用的速率和用时<br>
		 * 如统计QPS
		 */
		public Timer timer(final String name) {
			final String timeName = timerName(name);
			if (TIMER_CACHE.containsKey(timeName)) {
				return TIMER_CACHE.get(timeName);
			}

			final Timer timer = metricsReg.timer(timeName);
			TIMER_CACHE.put(timeName, timer);
			return timer;
		}

		private String requestName(final String namePrefix) {
			return name(namePrefix, "requests");
		}

		private String timerName(final String namePrefix) {
			return name(namePrefix, "calculation-duration");
		}
	}

	private static InnerCaller call = null;

	/**
	 * 中心部件 MetricRegistry。 它是程序中所有度量metric的容器。
	 */
	private static MetricRegistry metricsReg = null;
	/**
	 * Meter是一种只能自增的计数器，通常用来度量一系列事件发生的比率。他提供了平均速率，以及指数平滑平均速率，以及采样后的1分钟，5分钟，
	 * 15分钟速率。 metrics:事件总数，平均速率,包含1分钟，5分钟，15分钟的速率
	 */
	private static final Map<String, Meter> REQUESTS_METER_CACHE = new ConcurrentHashMap<>();
	private static final Map<String, Timer> TIMER_CACHE = new ConcurrentHashMap<>();

	public static InnerCaller getInstance() {
		if (call != null) {
			return call;
		}

		metricsReg = new MetricRegistry();

		call = new InnerCaller(metricsReg);

		// 注册metrics,每个1秒打印metrics到控制台
		// //Console
		final ScheduledReporter reporter = ConsoleReporter.forRegistry(metricsReg).convertRatesTo(TimeUnit.SECONDS)
				.convertDurationsTo(TimeUnit.MILLISECONDS).build();
		// //CSV
		// final ScheduledReporter reporter1 =
		// CsvReporter.forRegistry(metricsReg).formatFor(Locale.US)
		// .convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS)
		// .build(new File("/data/projects/metrics/"));
		// //SLF4J
		// final ScheduledReporter reporter2 =
		// Slf4jReporter.forRegistry(metricsReg)
		// .outputTo(LoggerFactory.getLogger("com.yueny.demo.metrics")).convertRatesTo(TimeUnit.SECONDS)
		// .convertDurationsTo(TimeUnit.MILLISECONDS).build();

		reporter.start(10, TimeUnit.SECONDS);
		// reporter.start(1, TimeUnit.MINUTES);

		return call;
	}

}
