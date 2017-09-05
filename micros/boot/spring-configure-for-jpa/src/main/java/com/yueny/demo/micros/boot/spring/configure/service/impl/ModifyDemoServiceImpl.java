package com.yueny.demo.micros.boot.spring.configure.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.reflect.TypeToken;
import com.yueny.demo.micros.boot.spring.configure.entry.ModifyDemoForJpaEntry;
import com.yueny.demo.micros.boot.spring.configure.repository.IModifyDemoRepository;
import com.yueny.demo.micros.boot.spring.configure.service.IModifyDemoService;
import com.yueny.demo.micros.boot.spring.configure.storager.cache.CacheDataHandler;
import com.yueny.demo.micros.boot.spring.configure.storager.cache.CacheService;

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

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.yueny.demo.micros.boot.spring.configure.service.IModifyDemoService#
	 * queryById(java.lang.Long)
	 */
	@Override
	@Cacheable(value = "queryByIdFor", keyGenerator = "wiselyKeyGenerator")
	public ModifyDemoForJpaEntry queryById(final Long id) {
		return modifyDemoRepository.findOne(id);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.yueny.demo.micros.boot.spring.configure.service.IModifyDemoService#
	 * queryByName(java.lang.String)
	 */
	@Override
	public ModifyDemoForJpaEntry queryByName(final String name) {
		final CacheDataHandler<ModifyDemoForJpaEntry> handler = new CacheDataHandler<ModifyDemoForJpaEntry>() {
			@Override
			public ModifyDemoForJpaEntry caller() {
				return modifyDemoRepository.findByName(name);
			}
		};

		return cacheService.process("queryByName-" + name, handler, 15L, ModifyDemoForJpaEntry.class);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.yueny.demo.micros.boot.spring.configure.service.IModifyDemoService#
	 * queryByType(java.lang.String)
	 */
	@Override
	public List<ModifyDemoForJpaEntry> queryByType(final String type) {
		// final String ts = cacheService.process("queryByType", new
		// CacheDataHandler<String>() {
		// @Override
		// public String caller() {
		// final List<ModifyDemoForJpaEntry> es =
		// modifyDemoRepository.queryByType(type);
		//
		// return JsonUtil.toJson(es);
		// }
		// }, 10L);
		// return JsonUtil.fromJson(ts, List.class,
		// ModifyDemoForJpaEntry.class);

		final CacheDataHandler<List<ModifyDemoForJpaEntry>> handler = new CacheDataHandler<List<ModifyDemoForJpaEntry>>() {
			@Override
			public List<ModifyDemoForJpaEntry> caller() {
				return modifyDemoRepository.queryByType(type);
			}
		};
		return cacheService.process("queryByType", handler, 10L, new TypeToken<List<ModifyDemoForJpaEntry>>() {
		});
	}

}
