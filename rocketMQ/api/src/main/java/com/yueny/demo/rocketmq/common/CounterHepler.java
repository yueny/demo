package com.yueny.demo.rocketmq.common;

public class CounterHepler {
	/**
	 * Atomically adds the delta to the current value for this key
	 *
	 * @param counter
	 *            The key for this metric
	 * @param delta
	 *            delta
	 * @return The updated value for this key
	 */
	public static long addAndGet(final long delta) {
		return Counter.getCounter().addAndGet(delta);
	}

	/**
	 * Atomically adds the delta to the current value for this key
	 *
	 * @param counter
	 *            The key for this metric
	 * @param delta
	 *            delta
	 * @return The updated value for this key
	 */
	public static long addAndGet(final String counter, final long delta) {
		return Counter.getCounter(counter).addAndGet(delta);
	}

	/**
	 * Atomically decrement the current value for this key by one<br>
	 * 并发计数,-1
	 *
	 * @param counter
	 *            counter The key for this metric
	 * @return The updated value for this key
	 */
	public static long decrement() {
		return Counter.getCounter().decrementAndGet();
	}

	/**
	 * Atomically decrement the current value for this key by one<br>
	 * 并发计数,-1
	 *
	 * @param counter
	 *            counter The key for this metric
	 * @return The updated value for this key
	 */
	public static long decrement(final String counter) {
		return Counter.getCounter(counter).decrementAndGet();
	}

	/**
	 * Retrieves the current value for this key
	 *
	 * @param counter
	 *            The key for this metric
	 * @return The current value for this key
	 */
	public static long get() {
		return Counter.getCounter().get();
	}

	/**
	 * Retrieves the current value for this key
	 *
	 * @param counter
	 *            The key for this metric
	 * @return The current value for this key
	 */
	public static long get(final String counter) {
		return Counter.getCounter(counter).get();
	}

	/**
	 * Atomically increments the current value for this key by one<br>
	 * 默认并发计数,+1
	 *
	 * @param counter
	 *            counter The key for this metric
	 * @return The updated value for this key
	 */
	public static long increment() {
		return Counter.getCounter().incrementAndGet();
	}

	/**
	 * Atomically increments the current value for this key by one<br>
	 * 并发计数,+1
	 *
	 * @param counter
	 *            counter The key for this metric
	 * @return The updated value for this key
	 */
	public static long increment(final String counter) {
		return Counter.getCounter(counter).incrementAndGet();
	}

}
