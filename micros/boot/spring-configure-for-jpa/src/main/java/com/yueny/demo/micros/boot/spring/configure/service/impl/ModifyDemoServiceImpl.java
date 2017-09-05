package com.yueny.demo.micros.boot.spring.configure.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.yueny.demo.micros.boot.spring.configure.entry.ModifyDemoForJpaEntry;
import com.yueny.demo.micros.boot.spring.configure.repository.IModifyDemoRepository;
import com.yueny.demo.micros.boot.spring.configure.service.IModifyDemoService;
import com.yueny.demo.micros.boot.spring.configure.storager.cache.CacheDataHandler;
import com.yueny.demo.micros.boot.spring.configure.storager.cache.CacheService;
import com.yueny.rapid.lang.json.JsonUtil;

@Component
@Transactional
public class ModifyDemoServiceImpl implements IModifyDemoService {
	@Autowired
	private CacheService cacheService;
	@Autowired
	private IModifyDemoRepository modifyDemoRepository;

	@Override
	public Long count() {
		return modifyDemoRepository.count();
	}

	@Override
	public List<ModifyDemoForJpaEntry> queryByType(final String type) {
		final String ts = cacheService.process("queryByType", new CacheDataHandler<String>() {
			@Override
			public String caller() {
				final List<ModifyDemoForJpaEntry> es = modifyDemoRepository.queryByType(type);

				return JsonUtil.toJson(es);
			}
		}, 5L);
		return JsonUtil.fromJson(ts, List.class, ModifyDemoForJpaEntry.class);

		// final CacheDataHandler<List<ModifyDemoForJpaEntry>> handler = new
		// CacheDataHandler<List<ModifyDemoForJpaEntry>>() {
		// @Override
		// public List<ModifyDemoForJpaEntry> caller() {
		// return modifyDemoRepository.queryByType(type);
		// }
		// };
		// return cacheService.process("queryByType", handler, 5L, new
		// TypeToken<List<ModifyDemoForJpaEntry>>() {
		// });
	}

}
