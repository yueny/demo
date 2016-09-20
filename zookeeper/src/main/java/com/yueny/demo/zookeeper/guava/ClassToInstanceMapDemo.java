package com.yueny.demo.zookeeper.guava;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import com.yueny.demo.zookeeper.demo1.bo.DemoBo;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月10日 下午6:27:54
 *
 */
public class ClassToInstanceMapDemo {
	public static void main(final String[] args) {
		final ClassToInstanceMap<DemoBo> classToInstanceMap = MutableClassToInstanceMap
				.create();

		final DemoBo bo1 = new DemoBo();
		bo1.setId("1");
		bo1.setName("1name名称");
		final DemoBo bo2 = new DemoBo();
		bo2.setId("2");
		bo2.setName("2name名称");
		classToInstanceMap.putInstance(DemoBo.class, bo2);

		// System.out.println("string:"+classToInstanceMap.getInstance(String.class));
		// System.out.println("integer:" +
		// classToInstanceMap.getInstance(Integer.class));

		final DemoBo d1 = classToInstanceMap.getInstance(DemoBo.class);
		System.out.println(d1);
	}
}
