package com.yueny.demo.capture.demo.cardbin;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yueny.demo.capture.BaseBizTest;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年1月10日 下午12:53:09
 *
 */
public class CardBinServiceImplImplTest extends BaseBizTest {
	@Autowired
	private ICardBinService cardBinService;

	@Test
	public void testSqlWithFile() {
		// "/tfs/select.sql"
		final Map<String, String> sqlMaps = cardBinService.sqlWithFile("/tfs/chinapay-2016-card-bin.xls");

		Assert.assertTrue(sqlMaps != null);
	}

}
