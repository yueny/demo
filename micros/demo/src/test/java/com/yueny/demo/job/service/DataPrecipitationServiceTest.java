package com.yueny.demo.job.service;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.yueny.demo.job.bo.ModifyDemoBo;
import com.yueny.rapid.lang.util.UuidUtil;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年11月16日 上午10:27:24
 *
 */
public class DataPrecipitationServiceTest extends BaseBizTest {
	@Autowired
	private IDataPrecipitationService dataPrecipitationService;

	@Test
	public void testInvock() {
		// 生成数据
		final List<ModifyDemoBo> ds = Lists.newArrayList();
		final String tempPre = "{\"body\":\"eyJjaGFyc2V0IjoiVVRGOCIsImNvbnRlbnQiOiIwLzAtLUp1c3QgZm9yIHRlc3QucHVzaCEiLCJldmVudFJlcXVlc3RUeXBlIjoiVEVTVF8yIiwic3ViamVjdCI6Iua2iOaBryJ9\",\"headers\":{\"MSG_ID\":\"";
		for (int i = 0; i < 1000; i++) {
			final ModifyDemoBo bo = new ModifyDemoBo();
			bo.setName(UuidUtil.getUuid());
			bo.setDesc("JOB_DEMO");
			bo.setType("Y");
			bo.setEventData(tempPre + bo.getName() + "\"}}");
			ds.add(bo);
		}

		dataPrecipitationService.insertList(ds);
	}

}
