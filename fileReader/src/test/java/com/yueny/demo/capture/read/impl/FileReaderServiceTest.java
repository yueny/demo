/**
 *
 */
package com.yueny.demo.capture.read.impl;

import java.io.File;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yueny.demo.capture.BaseBizTest;
import com.yueny.demo.capture.read.IFileReaderService;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月5日 上午11:05:44
 *
 */
public class FileReaderServiceTest extends BaseBizTest {
	@Autowired
	private IFileReaderService fileReaderService;

	@Test
	public void testGetLineNumberReader() {
		final LineNumberReader reader = fileReaderService.getLineNumberReader("D:/adcfg.json");
		Assert.assertNotNull(reader);

		try {
			final String line = reader.readLine();
			System.out.println(line);
			Assert.assertEquals(line, "{\"code\":0,\"data\":[],\"success\":true,\"timestamp\":1482646323191}");
		} catch (final IOException e) {
			e.printStackTrace();
		}

		final LineNumberReader reader2 = fileReaderService.getLineNumberReader("/tfs/select.sql");
		Assert.assertNotNull(reader2);

		try {
			final String line = reader2.readLine();
			System.out.println(line);
			Assert.assertEquals(line, "select * from modify_demo;");
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetPrintWriter() {
		final File file = new File("/tfs/select.sql");
		if (!file.exists()) {
			Assert.fail("文件不存在！");
		}

		final PrintWriter pr = fileReaderService.getPrintWriter(file);
		Assert.assertNotNull(pr);
	}
}
