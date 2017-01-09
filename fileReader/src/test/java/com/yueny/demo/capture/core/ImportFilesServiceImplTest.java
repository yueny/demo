package com.yueny.demo.capture.core;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yueny.demo.capture.BaseBizTest;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年1月9日 上午9:56:35
 *
 */
public class ImportFilesServiceImplTest extends BaseBizTest {
	@Autowired
	private IImportFilesService filesService;

	@Test
	public void testImportFile() {
		final boolean rs = filesService.importFile("/tfs/chinapay-2016-cardbin.xls");
		Assert.assertTrue(rs);
	}

}
