package com.yueny.demo.redis.dao;

import java.util.List;

import com.yueny.demo.redis.entry.ModifyDemoEntry;
import com.yueny.kapo.api.IClearTable;
import com.yueny.kapo.api.IDeleteTable;
import com.yueny.kapo.api.IWholeTableQuery;
import com.yueny.kapo.core.dao.biz.origin.IOriginDao;
import com.yueny.superclub.util.common.enums.YesNoType;

/**
 * 数据落地服务
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月10日 下午4:25:18
 *
 */
public interface IDataPrecipitationDao
		extends IOriginDao<ModifyDemoEntry>, IWholeTableQuery<ModifyDemoEntry>, IClearTable, IDeleteTable {
	/**
	 * 根据类型查询数据
	 */
	List<ModifyDemoEntry> queryByType(YesNoType type);
}
