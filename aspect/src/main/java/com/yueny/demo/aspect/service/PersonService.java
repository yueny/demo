package com.yueny.demo.aspect.service;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年3月17日 下午3:58:51
 *
 */
public interface PersonService {
	public String getPersonName(Integer id);

	public int save(String name);

	public Integer update(String name, Integer id);
}
