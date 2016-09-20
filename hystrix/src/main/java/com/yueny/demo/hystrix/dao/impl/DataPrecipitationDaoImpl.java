package com.yueny.demo.hystrix.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yueny.demo.hystrix.dao.IDataPrecipitationDao;
import com.yueny.demo.hystrix.entry.ModifyDemoEntry;
import com.yueny.kapo.api.annnotation.DbSchemaType;
import com.yueny.kapo.api.annnotation.TableSeg;
import com.yueny.kapo.core.condition.builder.QueryBuilder;
import com.yueny.kapo.core.dao.biz.origin.AbstractOrginDao;
import com.yueny.superclub.util.common.enums.YesNoType;

/**
 * 数据落地服务
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月5日 上午10:00:34
 *
 */
@DbSchemaType("TEST")
@TableSeg(tableName = "MODIFY_DEMO")
@Repository
public class DataPrecipitationDaoImpl extends AbstractOrginDao<ModifyDemoEntry> implements IDataPrecipitationDao {
	/*
	 * (non-Javadoc)
	 *
	 * @see com.yueny.kapo.core.dao.SingleTableDao#queryAll()
	 */
	@Override
	public List<ModifyDemoEntry> queryAll() {
		return super.queryAll();
	}

	@Override
	public List<ModifyDemoEntry> queryByType(final YesNoType type) {
		final QueryBuilder builder = QueryBuilder.builder().where("TYPE", type.name()).build();
		return super.queryListByColumns(builder);
	}

}
