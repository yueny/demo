package com.yueny.demo.capture.comp.impl;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import lombok.extern.log4j.Log4j;

@Log4j
public class FileStampImplTest {
	private static final String PATH = "/abc";
	private final FileStampImpl bizImpl = new FileStampImpl();

	@Test
	public void testCheckFileExist() {
		final File mockFile = new File(PATH);
		try {
			mockFile.createNewFile();
		} catch (final IOException e) {
			log.error(e);
		}

		Assert.assertTrue(bizImpl.checkFileExist(PATH));
	}
}
