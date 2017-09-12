package com.yueny.demo.micros.boot.spring.configure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yueny.demo.micros.boot.spring.configure.entry.ModifyDemoForJpaEntry;

/**
 * 数据库操作
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年9月4日 下午1:41:36
 *
 */
@Repository
public interface IModifyDemoRepository extends JpaRepository<ModifyDemoForJpaEntry, Long> {

	ModifyDemoForJpaEntry findByName(String name);

	/**
	 * @param desc
	 *            模糊查询
	 */
	List<ModifyDemoForJpaEntry> queryByDescLike(String desc);

	List<ModifyDemoForJpaEntry> queryByType(final String type);

}
