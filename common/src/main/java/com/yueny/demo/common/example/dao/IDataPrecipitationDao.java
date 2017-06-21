package com.yueny.demo.common.example.dao;

import java.util.List;

import com.yueny.demo.common.example.entry.ModifyDemoEntry;
import com.yueny.kapo.api.IClearTableDao;
import com.yueny.kapo.api.IDeleteTableDao;
import com.yueny.kapo.api.IMultipleTableDao;
import com.yueny.kapo.api.IWholeTableQueryDao;
import com.yueny.kapo.core.dao.biz.origin.IOriginDao;
import com.yueny.rapid.lang.util.enums.YesNoType;

/**
 * 数据落地服务
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月10日 下午4:25:18
 *
 */
public interface IDataPrecipitationDao extends IOriginDao<ModifyDemoEntry>, IWholeTableQueryDao<ModifyDemoEntry>,
		IClearTableDao, IDeleteTableDao, IMultipleTableDao<ModifyDemoEntry> {
	/**
	 * 根据分片项查询未处理的数据主键<br>
	 * select id from modify_demo where mod(ID,
	 * #{taskItemsharding})=${taskItemValues};<br>
	 * eg: select id from modify_demo where mod(ID, 5)=0
	 *
	 * @param taskTotalItemsharding
	 *            当前任务类型的任务队列数量
	 * @param shardingItem
	 *            当前分片项 0-N，运行在本作业服务器的分片序列号
	 * @param fetchDataNum
	 *            每次获取数据的数量
	 * @return
	 */
	List<Long> quertIdsBySharding(int taskTotalItemsharding, Integer shardingItem, Integer fetchDataNum);

	/**
	 * 根据分片项查询未处理的数据主键<br>
	 * select id from modify_demo where mod(ID,
	 * #{taskItemsharding})=${taskItemValues};<br>
	 * eg: select id from modify_demo where mod(ID, 5)=0
	 *
	 * @param taskTotalItemsharding
	 *            当前任务类型的任务队列数量
	 * @param shardingItem
	 *            当前分片项 0-N，运行在本作业服务器的分片序列号
	 * @param fetchDataNum
	 *            每次获取数据的数量
	 * @param type
	 *            状态
	 * @return
	 */
	List<Long> quertIdsBySharding(int taskTotalItemsharding, Integer shardingItem, Integer fetchDataNum,
			final YesNoType type);

	/**
	 * 根据分片项查询未处理的数据主键<br>
	 * select id from modify_demo where mod(ID, #{taskItemsharding}) in
	 * ${taskItemValues} limit #{fetchDataNum}<br>
	 * eg: select id from modify_demo where mod(ID, 5) in (1,2)
	 *
	 * @param taskTotalItemsharding
	 *            当前任务类型的任务队列数量
	 * @param shardingItems
	 *            当前分片项 0-N，运行在本作业服务器的分片序列号
	 * @param fetchDataNum
	 *            每次获取数据的数量
	 * @return
	 */
	List<Long> quertIdsBySharding(int taskTotalItemsharding, List<Integer> shardingItems, Integer fetchDataNum);

	/**
	 * 查询全表的数据实体主键列表<br>
	 *
	 * @return 对应的数据实体主键
	 */
	List<Long> queryAllIds();

	/**
	 * 根据类型查询数据
	 */
	List<ModifyDemoEntry> queryByType(YesNoType type);

	/**
	 * 根据主键更新状态
	 *
	 * @param primaryId
	 *            主键
	 * @param type
	 *            状态
	 * @return 是否成功
	 */
	boolean setInactive(final Long primaryId, final YesNoType type);
}
