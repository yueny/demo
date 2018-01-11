package com.yueny.demo.common.example.api;

import java.util.List;

/**
 * 分片项查询
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2018年1月11日 上午11:53:52
 * @since
 */
public interface IShardingQueryService<T> {
	/**
	 * 根据分片项查询数据<br>
	 * select * from table where mod(ID,
	 * #{taskTotalItemsharding})=${taskItemValues};<br>
	 * eg: select id from table where mod(ID, 5)=0
	 *
	 * @param taskTotalItemsharding
	 *            当前任务类型的任务队列数量
	 * @param taskItemValues
	 *            当前分片项 0-N，运行在本作业服务器的分片序列号
	 */
	List<T> queryListBySharding(final int taskTotalItemsharding, final Integer taskItemValues);

	/**
	 * 根据分片项查询数据<br>
	 * select * from table where mod(ID,
	 * #{taskTotalItemsharding})=${taskItemValues} limit fetchDataNum;<br>
	 * eg: select id from table where mod(ID, 5)=0 limit fetchDataNum
	 *
	 * @param taskTotalItemsharding
	 *            当前任务类型的任务队列数量
	 * @param taskItemValues
	 *            当前分片项 0-N，运行在本作业服务器的分片序列号
	 * @param fetchDataNum
	 *            每次获取数据的数量
	 */
	List<T> queryListBySharding(final int taskTotalItemsharding, final Integer taskItemValues,
			final Integer fetchDataNum);

}
