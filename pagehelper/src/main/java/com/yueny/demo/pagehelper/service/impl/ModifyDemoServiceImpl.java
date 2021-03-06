package com.yueny.demo.pagehelper.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yueny.demo.pagehelper.bo.ModifyDemoBo;
import com.yueny.demo.pagehelper.dao.mapper.IModifyDemoMapper;
import com.yueny.demo.pagehelper.entry.ModifyDemoEntry;
import com.yueny.demo.pagehelper.service.BaseSevice;
import com.yueny.demo.pagehelper.service.IModifyDemoService;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月5日 上午9:57:38
 *
 */
@Service
public class ModifyDemoServiceImpl extends BaseSevice implements IModifyDemoService {
	@Autowired
	private IModifyDemoMapper modifyDemoDao;

	/*
	 * (non-Javadoc)
	 *
	 * @see com.yueny.kapo.example.service.IDemoService#queryAll()
	 */
	@Override
	public List<ModifyDemoBo> queryAll() {
		final List<ModifyDemoEntry> entrys = modifyDemoDao.selectAll();

		if (CollectionUtils.isEmpty(entrys)) {
			return Collections.emptyList();
		}

		return map(entrys, ModifyDemoBo.class);
	}

}
