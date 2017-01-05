package com.yueny.demo.pagehelper.dao.mapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yueny.demo.pagehelper.dao.BaseDaoTest;
import com.yueny.demo.pagehelper.dao.comp.MybatisHelper;
import com.yueny.demo.pagehelper.entry.ModifyDemoEntry;

public class IModifyDemoMapperTest extends BaseDaoTest {
	@Autowired
	private MybatisHelper mybatisHelper;

	/**
	 * 使用Mapper接口调用时，使用PageHelper.startPage效果更好，不需要添加Mapper接口参数
	 */
	@Test
	public void testPageSize10() {
		final SqlSession sqlSession = mybatisHelper.getSqlSession();

		final IModifyDemoMapper scriptMapper = sqlSession.getMapper(IModifyDemoMapper.class);
		try {
			// //获取第1页，10条内容，默认查询总数count
			// PageHelper.startPage(1, 10);
			final List<ModifyDemoEntry> list = scriptMapper.selectAll();
			Assert.assertTrue(list.size() > 0);

			// PageInfo<Country> page = new PageInfo<Country>(list);
			// assertEquals(1, page.getPageNum());
			// assertEquals(10, page.getPageSize());
			// assertEquals(1, page.getStartRow());
			// assertEquals(10, page.getEndRow());
			// assertEquals(183, page.getTotal());
			// assertEquals(19, page.getPages());
			// assertEquals(1, page.getFirstPage());
			// assertEquals(8, page.getLastPage());
			// assertEquals(true, page.isIsFirstPage());
			// assertEquals(false, page.isIsLastPage());
			// assertEquals(false, page.isHasPreviousPage());
			// assertEquals(true, page.isHasNextPage());

			//
			// //获取第2页，10条内容，默认查询总数count
			// PageHelper.startPage(2, 10);
			// list = countryMapper.selectAll();
			// page = new PageInfo<Country>(list);
			// assertEquals(2, page.getPageNum());
			// assertEquals(10, page.getPageSize());
			// assertEquals(11, page.getStartRow());
			// assertEquals(20, page.getEndRow());
			// assertEquals(183, page.getTotal());
			// assertEquals(19, page.getPages());
			// assertEquals(1, page.getFirstPage());
			// assertEquals(8, page.getLastPage());
			// assertEquals(false, page.isIsFirstPage());
			// assertEquals(false, page.isIsLastPage());
			// assertEquals(true, page.isHasPreviousPage());
			// assertEquals(true, page.isHasNextPage());
			//
			//
			// //获取第19页，10条内容，默认查询总数count
			// PageHelper.startPage(19, 10);
			// list = countryMapper.selectAll();
			// page = new PageInfo<Country>(list);
			// assertEquals(19, page.getPageNum());
			// assertEquals(10, page.getPageSize());
			// assertEquals(181, page.getStartRow());
			// assertEquals(183, page.getEndRow());
			// assertEquals(183, page.getTotal());
			// assertEquals(19, page.getPages());
			// assertEquals(12, page.getFirstPage());
			// assertEquals(19, page.getLastPage());
			// assertEquals(false, page.isIsFirstPage());
			// assertEquals(true, page.isIsLastPage());
			// assertEquals(true, page.isHasPreviousPage());
			// assertEquals(false, page.isHasNextPage());

		} finally {
			sqlSession.close();
		}
	}
}
