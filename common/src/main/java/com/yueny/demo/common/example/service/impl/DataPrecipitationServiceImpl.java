package com.yueny.demo.common.example.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yueny.demo.common.example.bo.ModifyDemoBo;
import com.yueny.demo.common.example.dao.IDataPrecipitationDao;
import com.yueny.demo.common.example.entry.ModifyDemoEntry;
import com.yueny.demo.common.example.service.BaseSevice;
import com.yueny.demo.common.example.service.IDataPrecipitationService;
import com.yueny.rapid.lang.util.enums.YesNoType;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月5日 上午9:57:38
 *
 */
@Service
public class DataPrecipitationServiceImpl extends BaseSevice implements IDataPrecipitationService {
	@Autowired
	private IDataPrecipitationDao dataPrecipitationDao;

	@Override
	public ModifyDemoBo findById(final Long primaryId) {
		final ModifyDemoEntry entry = dataPrecipitationDao.queryByID(primaryId);

		if (entry == null) {
			return null;
		}

		return map(entry, ModifyDemoBo.class);
	}

	@Override
	public Long insert(final ModifyDemoBo data) {
		return dataPrecipitationDao.insert(map(data, ModifyDemoEntry.class));
	}

	@Override
	public int insertList(final List<ModifyDemoBo> ds) {
		final List<ModifyDemoEntry> entrys = new ArrayList<>();
		for (final ModifyDemoBo bo : ds) {
			entrys.add(map(bo, ModifyDemoEntry.class));
		}

		return dataPrecipitationDao.insertList(entrys);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.yueny.demo.job.service.IDataPrecipitationService#quertIdsBySharding(
	 * int)
	 */
	@Override
	public List<Long> quertIdsBySharding(final int taskItemsharding, final List<Integer> taskItemValues,
			final Integer fetchDataNum) {
		return dataPrecipitationDao.quertIdsBySharding(taskItemsharding, taskItemValues, fetchDataNum);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see com.yueny.demo.job.service.IDataPrecipitationService#queryAll()
	 */
	@Override
	public List<ModifyDemoBo> queryAll() {
		final List<ModifyDemoEntry> entrys = dataPrecipitationDao.queryAll();

		if (CollectionUtils.isEmpty(entrys)) {
			return Collections.emptyList();
		}

		return map(entrys, ModifyDemoBo.class);
	}

	@Override
	public Long queryAllCount() {
		final Long count = dataPrecipitationDao.queryAllCount();

		if (count == null) {
			return 0L;
		}
		return count;
	}

	@Override
	public List<Long> queryAllIds() {
		return dataPrecipitationDao.queryAllIds();
	}

	@Override
	public List<ModifyDemoBo> queryByType(final YesNoType type) {
		final List<ModifyDemoEntry> entrys = dataPrecipitationDao.queryByType(type);

		if (CollectionUtils.isEmpty(entrys)) {
			return Collections.emptyList();
		}

		return map(entrys, ModifyDemoBo.class);
	}

	@Override
	public boolean setInactive(final Long primaryId, final YesNoType type) {
		return dataPrecipitationDao.setInactive(primaryId, type);
	}

	@Override
	public boolean update(final ModifyDemoBo tobeUpdate) {
		return dataPrecipitationDao.update(map(tobeUpdate, ModifyDemoEntry.class));
	}

}
