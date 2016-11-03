package com.yueny.demo.metrics.intercepter;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Configuration;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ScheduledReporter;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年11月2日 下午6:26:44
 *
 */
@Configuration
@EnableMetrics
public class SpringConfiguringClass extends MetricsConfigurerAdapter {

	@Override
	public void configureReporters(final MetricRegistry metricRegistry) {
		// 注册metrics,每n秒打印metrics到控制台
		// Console
		final ScheduledReporter reporter = ConsoleReporter.forRegistry(metricRegistry).convertRatesTo(TimeUnit.SECONDS)
				.convertDurationsTo(TimeUnit.MILLISECONDS).build();

		// CSV
		// final ScheduledReporter reporter1 =
		// CsvReporter.forRegistry(metricRegistry).formatFor(Locale.US)
		// .convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS)
		// .build(new File("/data/projects/metrics/"));

		// SLF4J
		// final ScheduledReporter reporter2 =
		// Slf4jReporter.forRegistry(metricsReg)
		// .outputTo(LoggerFactory.getLogger("com.yueny.demo.metrics")).convertRatesTo(TimeUnit.SECONDS)
		// .convertDurationsTo(TimeUnit.MILLISECONDS).build();

		// Graphite
		// final Graphite graphite = new Graphite(new
		// InetSocketAddress("graphite.example.com", 2003));
		// final GraphiteReporter reporter1 =
		// GraphiteReporter.forRegistry(metricRegistry)
		// .prefixedWith("com.yueny.demo.metrics").convertRatesTo(TimeUnit.SECONDS)
		// .convertDurationsTo(TimeUnit.MILLISECONDS).filter(MetricFilter.ALL).build(graphite);

		// Ganglia
		// final GMetric ganglia = new GMetric("ganglia.example.com", 8649,
		// UDPAddressingMode.MULTICAST, 1);
		// final GangliaReporter reporter =
		// GangliaReporter.forRegistry(metricRegistry)
		// .convertRatesTo(TimeUnit.SECONDS)
		// .convertDurationsTo(TimeUnit.MILLISECONDS)
		// .build(ganglia);

		reporter.start(10, TimeUnit.SECONDS);
		// reporter1.start(1, TimeUnit.MINUTES);
	}

}
