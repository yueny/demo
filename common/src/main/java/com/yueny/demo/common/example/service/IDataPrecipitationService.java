package com.yueny.demo.common.example.service;

import java.util.List;

import com.yueny.demo.common.example.bo.ModifyDemoBo;
import com.yueny.rapid.lang.util.enums.YesNoType;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月5日 上午9:56:50
 *
 */
public interface IDataPrecipitationService {
	/**
	 * 根据主键查询
	 */
	ModifyDemoBo findById(Long primaryId);

	/**
	 * 新增
	 */
	Long insert(ModifyDemoBo data);

	/**
	 * 新增数据列表
	 *
	 * @param ds
	 *            待新增的数据实体
	 * @return 新增成功条数
	 */
	int insertList(final List<ModifyDemoBo> ds);

	/**
	 * 根据分片项查询未处理的数据主键
	 *
	 * @param taskItemsharding
	 *            当前任务类型的任务队列数量
	 * @param taskItemValues
	 *            当前调度服务器，分配到的可处理队列
	 * @param fetchDataNum
	 *            每次获取数据的数量
	 * @return
	 */
	List<Long> quertIdsBySharding(int taskItemsharding, List<Integer> taskItemValues, Integer fetchDataNum);

	/**
	 * @return
	 */
	List<ModifyDemoBo> queryAll();

	/**
	 * 查询表内数据数量
	 *
	 * @return 总数
	 */
	Long queryAllCount();

	/**
	 * 查询全表的数据实体主键列表<br>
	 *
	 * @return 对应的数据实体主键
	 */
	List<Long> queryAllIds();

	/**
	 * 根据类型查询数据
	 */
	List<ModifyDemoBo> queryByType(YesNoType type);

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

	/**
	 * 根据主键全值更新数据<br>
	 * 该操作会更新更新版本列,更新修改人,处理更新时间
	 *
	 * @param tobeUpdate
	 *            待更新数据
	 * @return 是否成功
	 */
	boolean update(final ModifyDemoBo tobeUpdate);
}
