package com.yueny.demo.micros.boot.spring.configure.service;

import java.util.Collection;

import com.yueny.demo.micros.boot.spring.configure.entry.ModifyDemoForJpaEntry;

public interface IModifyDemoService {
	Long count();

	Collection<ModifyDemoForJpaEntry> queryByType(String type);
}
