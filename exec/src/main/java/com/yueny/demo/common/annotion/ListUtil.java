package com.yueny.demo.common.annotion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

/**
 * 列表工具类
 * 
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年4月13日 上午8:51:47
 *
 */
public class ListUtil {
	/**
	 * 数据分片
	 */
	public static <T> List<List<T>> partition(final List<T> baseList, final Integer partitionSize) {
		Assert.notEmpty(baseList);
		Assert.isTrue(partitionSize != null && partitionSize > 0, "partionLimit is illegal.");

		final int totalCount = baseList.size();
		final int partitionCount = totalCount % partitionSize > 0 ? totalCount / partitionSize + 1
				: totalCount / partitionSize;
		final List<List<T>> partitionList = new ArrayList<List<T>>();
		for (int index = 1; index <= partitionCount; index++) {
			if (index < partitionCount) {
				final List<T> subList = baseList.subList((index - 1) * partitionSize, partitionSize * index);
				partitionList.add(subList);
			} else {
				final List<T> subList = baseList.subList((index - 1) * partitionSize, totalCount);
				partitionList.add(subList);
			}
		}
		return partitionList;
	}

}
