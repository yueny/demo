package com.yueny.demo.pagehelper.dao.comp;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yueny.demo.pagehelper.dao.BaseDaoTest;
import com.yueny.demo.pagehelper.dao.mapper.IModifyDemoMapper;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年1月5日 下午2:31:35
 *
 */
public class MybatisHelperTest extends BaseDaoTest {
	@Autowired
	private MybatisHelper mybatisHelper;

	/**
	 * 使用Mapper接口调用时，使用PageHelper.startPage效果更好，不需要添加Mapper接口参数
	 */
	@Test
	public void testPageSize10() {
		final SqlSession sqlSession = mybatisHelper.getSqlSession();

		final IModifyDemoMapper scriptMapper = sqlSession.getMapper(IModifyDemoMapper.class);

	}

}
