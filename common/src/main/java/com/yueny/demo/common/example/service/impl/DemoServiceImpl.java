package com.yueny.demo.common.example.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yueny.demo.common.example.bo.DemoBo;
import com.yueny.demo.common.example.dao.IDemoDao;
import com.yueny.demo.common.example.entry.DemoEntry;
import com.yueny.demo.common.example.service.BaseSevice;
import com.yueny.demo.common.example.service.IDemoService;
import com.yueny.rapid.lang.util.enums.YesNoType;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月5日 上午9:57:38
 *
 */
@Service
public class DemoServiceImpl extends BaseSevice implements IDemoService {
	@Autowired
	private IDemoDao demoDao;

	@Override
	public DemoBo findById(final Long primaryId) {
		final DemoEntry entry = demoDao.queryByID(primaryId);

		if (entry == null) {
			return null;
		}

		return map(entry, DemoBo.class);
	}

	@Override
	public Long insert(final DemoBo data) {
		return demoDao.insert(map(data, DemoEntry.class));
	}

	@Override
	public int insertList(final List<DemoBo> ds) {
		final List<DemoEntry> entrys = new ArrayList<>();
		for (final DemoBo bo : ds) {
			entrys.add(map(bo, DemoEntry.class));
		}

		return demoDao.insertList(entrys);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.yueny.demo.job.service.IDataPrecipitationService#quertIdsBySharding(
	 * int, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<Long> quertIdsBySharding(final int taskTotalItemsharding, final Integer shardingItem,
			final Integer fetchDataNum) {
		return demoDao.quertIdsBySharding(taskTotalItemsharding, shardingItem, fetchDataNum);
	}

	@Override
	public List<Long> quertIdsBySharding(final int taskTotalItemsharding, final Integer shardingItem,
			final Integer fetchDataNum, final YesNoType type) {
		return demoDao.quertIdsBySharding(taskTotalItemsharding, shardingItem, fetchDataNum, type);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.yueny.demo.job.service.IDataPrecipitationService#quertIdsBySharding(
	 * int)
	 */
	@Override
	public List<Long> quertIdsBySharding(final int taskTotalItemsharding, final List<Integer> shardingItems,
			final Integer fetchDataNum) {
		return demoDao.quertIdsBySharding(taskTotalItemsharding, shardingItems, fetchDataNum);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.yueny.demo.job.service.IDataPrecipitationService#queryAll()
	 */
	@Override
	public List<DemoBo> queryAll() {
		final List<DemoEntry> entrys = demoDao.queryAll();

		if (CollectionUtils.isEmpty(entrys)) {
			return Collections.emptyList();
		}

		return map(entrys, DemoBo.class);
	}

	@Override
	public Long queryAllCount() {
		final Long count = demoDao.queryAllCount();

		if (count == null) {
			return 0L;
		}
		return count;
	}

	@Override
	public List<Long> queryAllIds() {
		return demoDao.queryAllIds();
	}

	@Override
	public List<DemoBo> queryByType(final YesNoType type) {
		final List<DemoEntry> entrys = demoDao.queryByType(type);

		if (CollectionUtils.isEmpty(entrys)) {
			return Collections.emptyList();
		}

		return map(entrys, DemoBo.class);
	}

	@Override
	public boolean setInactive(final Long primaryId, final YesNoType type) {
		return demoDao.setInactive(primaryId, type);
	}

	@Override
	public boolean update(final DemoBo tobeUpdate) {
		return demoDao.update(map(tobeUpdate, DemoEntry.class));
	}

}
