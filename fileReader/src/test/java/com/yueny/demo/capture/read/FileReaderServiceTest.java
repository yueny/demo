/**
 *
 */
package com.yueny.demo.capture.read;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yueny.demo.capture.BaseBizTest;

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
		final List<String> lines = fileReaderService.readLines("D:/adcfg.json");

		System.out.println(lines);
		Assert.assertNotNull(lines);
	}

}
