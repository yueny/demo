package com.yueny.demo.metrics;

import java.util.Map;
import java.util.Random;

import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;

/**
 * Health Check (健康检查)
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年11月1日 下午4:41:22
 *
 */
public class DatabaseHealthCheckMain extends HealthCheck {
	/**
	 * 模拟Database对象
	 */
	private static class Database {
		/**
		 * 模拟database的ping方法
		 *
		 * @return 随机返回boolean值
		 */
		public boolean ping() {
			final Random random = new Random();
			return random.nextBoolean();
		}
	}

	private static final HealthCheckRegistry healthChecksRegistry = new HealthCheckRegistry();

	public static void main(final String[] args) {
		healthChecksRegistry.register("database1", new DatabaseHealthCheckMain(new Database()));
		healthChecksRegistry.register("database2", new DatabaseHealthCheckMain(new Database()));

		while (true) {
			for (final Map.Entry<String, Result> entry : healthChecksRegistry.runHealthChecks().entrySet()) {
				if (entry.getValue().isHealthy()) {
					// ok
					System.out.println(entry.getKey() + ": OK");
					continue;
				}

				// error
				System.err.println(entry.getKey() + ": FAIL, error message: " + entry.getValue().getMessage());
				final Throwable e = entry.getValue().getError();
				if (e != null) {
					e.printStackTrace();
				}
			}

			try {
				Thread.sleep(1000);
			} catch (final InterruptedException e) {
				// .
			}
		}
	}

	private final Database database;

	public DatabaseHealthCheckMain(final Database database) {
		this.database = database;
	}

	@Override
	protected Result check() throws Exception {
		if (database.ping()) {
			return Result.healthy();
		}

		return Result.unhealthy("Can't ping database.");
	}

}
