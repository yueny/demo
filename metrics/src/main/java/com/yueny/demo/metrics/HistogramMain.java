package com.yueny.demo.metrics;

import static com.codahale.metrics.MetricRegistry.name;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.codahale.metrics.Histogram;
import com.yueny.demo.metrics.util.MetricRegistryHelperDemo;

/**
 * 直方图<br>
 * Histogram可以为数据流提供统计数据。 除了最大值，最小值，平均值外，它还可以测量 中值(median)，百分比比如XX%这样的Quantile数据
 * <br>
 * Histograms主要使用来统计数据的分布情况，最大值、最小值、平均值、中位数，百分比（75%、90%、95%、98%、99%和99.9%）。例如，
 * 需要统计某个页面的请求响应时间分布情况，可以使用该种类型的Metrics进行统计。
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年10月28日 下午2:22:21
 *
 */
public class HistogramMain {
	private static final Random rn = new Random();

	public static void main(final String args[]) {
		final Histogram histogram = MetricRegistryHelperDemo.getInstance().getReg()
				.histogram(name(HistogramMain.class, "histogram"));

		while (true) {
			histogram.update((int) (rn.nextDouble() * 100));
			waitByMillSeconds(100L);
		}
	}

	private static void waitByMillSeconds(final Long ms) {
		try {
			TimeUnit.MILLISECONDS.sleep(ms);
		} catch (final InterruptedException e) {
		}
	}

}
