package com.yueny.demo.zookeeper.guava;

import java.util.Collections;
import java.util.Map;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

/**
 * guava Table demo
 *
 * 相当于有两个key的map
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月10日 下午4:22:18
 *
 */
public class TableDemo {
	public static void main(final String[] args) {
		final Table<Integer, Integer, Long> personTable = HashBasedTable
				.create();
		personTable.put(1, 20, 1000L);
		personTable.put(0, 30, 2000L);
		personTable.put(0, 25, 3000L);
		personTable.put(1, 50, 4000L);
		personTable.put(0, 27, 5000L);
		personTable.put(1, 29, 6000L);
		personTable.put(0, 33, 7000L);
		personTable.put(1, 66, 8000L);

		// 1,得到行集合
		final Map<Integer, Long> rowMap = personTable.row(0);
		System.out.println(rowMap);
		final int maxAge = Collections.max(rowMap.keySet());
		System.out.println(maxAge);
	}

}
