package com.yueny.demo.common.example.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yueny.demo.common.example.dao.IDataPrecipitationDao;
import com.yueny.demo.common.example.entry.ModifyDemoEntry;
import com.yueny.kapo.api.annnotation.DbSchemaType;
import com.yueny.kapo.api.annnotation.TableSeg;
import com.yueny.kapo.core.condition.builder.QueryBuilder;
import com.yueny.kapo.core.condition.builder.UpdateBuilder;
import com.yueny.kapo.core.condition.column.operand.enums.BasicSqlOperand;
import com.yueny.kapo.core.dao.biz.origin.AbstractOrginDao;
import com.yueny.rapid.lang.util.enums.YesNoType;

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
	 * @see com.yueny.demo.job.dao.IDataPrecipitationDao#quertIdsBySharding(int,
	 * java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Long> queryIdsBySharding(final int taskTotalItemsharding, final Integer taskItemValues) {
		final QueryBuilder builder = QueryBuilder.builder()
				.where("mod(ID, " + taskTotalItemsharding + ")", taskItemValues).build();

		return super.queryEntryIdsByColumns(builder);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.yueny.demo.job.dao.IDataPrecipitationDao#quertIdsBySharding(int,
	 * java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Long> queryIdsBySharding(final int taskTotalItemsharding, final Integer taskItemValues,
			final Integer fetchDataNum) {
		final QueryBuilder builder = QueryBuilder.builder()
				.where("mod(ID, " + taskTotalItemsharding + ")", taskItemValues).limit(fetchDataNum).build();

		return super.queryEntryIdsByColumns(builder);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.yueny.demo.job.dao.IDataPrecipitationDao#quertIdsBySharding(int,
	 * java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Long> queryIdsBySharding(final int taskTotalItemsharding, final Integer taskItemValues,
			final Integer fetchDataNum, final YesNoType type) {
		final QueryBuilder builder = QueryBuilder.builder().where("TYPE", type.name())
				.where("mod(ID, " + taskTotalItemsharding + ")", taskItemValues).limit(fetchDataNum).build();

		return super.queryEntryIdsByColumns(builder);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.yueny.demo.job.dao.IDataPrecipitationDao#quertIdsBySharding(int)
	 */
	@Override
	public List<Long> queryIdsBySharding(final int taskTotalItemsharding, final List<Integer> taskItemValues,
			final Integer fetchDataNum) {
		final QueryBuilder builder = QueryBuilder.builder().where("TYPE", "N")
				.where("mod(ID, " + taskTotalItemsharding + ")", BasicSqlOperand.IN, taskItemValues).limit(fetchDataNum)
				.build();

		return super.queryEntryIdsByColumns(builder);
	}

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
	public List<Long> queryAllIds() {
		// return super.queryEntryIdsByColumns();

		final QueryBuilder builder = QueryBuilder.builder().limit(50).build();
		return super.queryEntryIdsByColumns(builder);
	}

	@Override
	public List<ModifyDemoEntry> queryByType(final YesNoType type) {
		final QueryBuilder builder = QueryBuilder.builder().where("TYPE", type.name()).build();
		return super.queryListByColumns(builder);
	}

	@Override
	public boolean setInactive(final Long primaryId, final YesNoType type) {
		final UpdateBuilder builder = UpdateBuilder.builder().set("ID", primaryId).where("TYPE", type.name()).build();
		return super.updateByColumns(builder) == 1;
	}

}
