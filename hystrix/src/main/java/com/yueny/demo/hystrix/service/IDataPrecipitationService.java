package com.yueny.demo.hystrix.service;

import java.util.List;

import com.yueny.demo.hystrix.bo.ModifyDemoBo;
import com.yueny.superclub.util.common.enums.YesNoType;

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
	 * @return
	 */
	List<ModifyDemoBo> queryAll();

	/**
	 * 根据类型查询数据
	 */
	List<ModifyDemoBo> queryByType(YesNoType type);

}
