package com.yueny.demo.job.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.yueny.demo.job.entry.ModifyDemoEntry;
import com.yueny.kapo.api.biz.ISqlMapper;

/**
 * 数据落地服务
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月10日 下午4:25:18
 *
 */
public interface IDataPrecipitationMapper extends ISqlMapper {
	/**
	 * 根据类型查询数据
	 */
	@Select("SELECT * FROM MODIFY_DEMO WHERE TYPE = #{type}")
	List<ModifyDemoEntry> queryByType(@Param(value = "type") String type);

}
