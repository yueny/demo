package com.yueny.demo.redis.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yueny.demo.redis.bo.ModifyDemoBo;
import com.yueny.demo.redis.dao.IDataPrecipitationDao;
import com.yueny.demo.redis.entry.ModifyDemoEntry;
import com.yueny.demo.redis.service.BaseSevice;
import com.yueny.demo.redis.service.IDataPrecipitationService;
import com.yueny.superclub.util.common.enums.YesNoType;

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

	/*
	 * (non-Javadoc)
	 *
	 * @see com.yueny.kapo.example.service.IDemoService#queryAll()
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
	public List<ModifyDemoBo> queryByType(final YesNoType type) {
		final List<ModifyDemoEntry> entrys = dataPrecipitationDao.queryByType(type);

		if (CollectionUtils.isEmpty(entrys)) {
			return Collections.emptyList();
		}

		return map(entrys, ModifyDemoBo.class);
	}

}
