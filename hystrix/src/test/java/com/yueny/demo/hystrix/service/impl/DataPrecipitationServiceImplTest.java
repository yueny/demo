/**
 *
 */
package com.yueny.demo.hystrix.service.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yueny.demo.hystrix.bo.ModifyDemoBo;
import com.yueny.demo.hystrix.service.BaseBizTest;
import com.yueny.demo.hystrix.service.IDataPrecipitationService;
import com.yueny.superclub.util.common.enums.YesNoType;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月5日 上午11:05:44
 *
 */
public class DataPrecipitationServiceImplTest extends BaseBizTest {
	@Autowired
	private IDataPrecipitationService dataPrecipitationService;

	@Test
	public void testFindById() {
		final ModifyDemoBo bo = dataPrecipitationService.findById(8L);

		Assert.assertEquals(bo.getId(), 8L);

		// if (JsonUtil.jsonFormatCheck(bo.getEventData())) {
		// final JSONEvent event = JsonUtil.fromJson(bo.getEventData(),
		// JSONEvent.class);
		// System.out.println(event);
		// }
	}

	@Test
	public void testInsert() {
		final ModifyDemoBo bo = new ModifyDemoBo();
		bo.setName("testInsert");
		bo.setDesc("testInsertDESC");
		bo.setType(YesNoType.N.name());

		final Long id = dataPrecipitationService.insert(bo);

		Assert.assertEquals(id.longValue(), 2L);
	}

	@Test
	public void testQueryAll() {
		final List<ModifyDemoBo> lists = dataPrecipitationService.queryAll();

		Assert.assertEquals(1, lists.size());
	}

	@Test
	public void testQueryByType() {
		final List<ModifyDemoBo> lists = dataPrecipitationService.queryByType(YesNoType.Y);

		Assert.assertTrue(lists.size() != 1);
	}

}
