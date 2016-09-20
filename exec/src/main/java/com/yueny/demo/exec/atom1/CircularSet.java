package com.yueny.demo.exec.atom1;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月24日 下午7:44:54
 *
 */
public class CircularSet {
	private final int[] array;
	private int index = 0;
	private final int len;

	public CircularSet(final int size) {
		array = new int[size];
		len = size;

		for (int i = 0; i < size; i++) {
			array[i] = -1;
		}
	}

	public synchronized void add(final int i) {
		array[index] = i;
		index = ++index % len;
	}

	public synchronized boolean contains(final int val) {
		for (int i = 0; i < len; i++) {
			if (array[i] == val) {
				return true;
			}
		}
		return false;
	}
}
