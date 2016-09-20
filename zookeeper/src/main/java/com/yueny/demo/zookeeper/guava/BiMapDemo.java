package com.yueny.demo.zookeeper.guava;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月10日 下午6:21:02
 *
 */
public class BiMapDemo {
	public static void main(final String[] args) {
		// 双向map
		final BiMap<Integer, String> biMap = HashBiMap.create();

		// 不允许重复value。可以相同会被覆盖掉旧值
		biMap.put(1, "hello");
		biMap.put(2, "helloa");
		biMap.put(3, "world");
		biMap.put(4, "worldb");
		biMap.put(5, "my");
		biMap.put(6, "myc");

		final int value = biMap.inverse().get("my");
		System.out.println("my --" + value);
		final String my = biMap.get(5);
		System.out.println("5 --" + my);
	}
}
