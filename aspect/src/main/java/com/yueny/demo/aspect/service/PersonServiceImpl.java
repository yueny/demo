package com.yueny.demo.aspect.service;

import org.springframework.stereotype.Service;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年3月17日 下午3:59:07
 *
 */
@Service
public class PersonServiceImpl implements PersonService {

	@Override
	public String getPersonName(final Integer id) {
		System.out.println("我是getPersonName()方法");
		return "a";
	}

	@Override
	public int save(final String name) {
		System.out.println("我是save()方法");
		return 0;
	}

	@Override
	public Integer update(final String name, final Integer id) {
		System.out.println("我是update()方法");
		return 1;
	}

}
