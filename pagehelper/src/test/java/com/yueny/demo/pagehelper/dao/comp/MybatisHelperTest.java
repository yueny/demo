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
	 */
	@Test
	public void testGetSqlSession() {
		final SqlSession sqlSession = mybatisHelper.getSqlSession();

		final IModifyDemoMapper scriptMapper = sqlSession.getMapper(IModifyDemoMapper.class);

	}

	@Test
	public void testRunnerSqlFile() {
		mybatisHelper.runnerSqlFile("tfs/select.sql");
	}

	@Test
	public void testRunnerSqlScript() {
		mybatisHelper.runnerSqlScript("select * from modify_demo");
	}
}
