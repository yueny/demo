package com.yueny.demo.capture.comp.impl;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import lombok.SneakyThrows;

public class FileRemoverImplTest {
	private final FileRemoverImpl bizImpl = new FileRemoverImpl();

	@Test
	@SneakyThrows(IOException.class)
	public void testMoveToNewPath() {
		final File oldFilePath = new File("/old");
		oldFilePath.mkdir();
		final File newFilePath = new File("/new");
		newFilePath.mkdir();
		final String newFileName = "new.txt";
		final String oldFileName = "old.txt";
		final File oldFile = new File(oldFilePath, oldFileName);
		oldFile.createNewFile();
		final File newFile = new File(newFilePath, newFileName);
		newFile.createNewFile();

		Assert.assertTrue(bizImpl.moveToNewPath("/old", "/new", oldFileName, "new2.txt"));
	}

	@Test
	@SneakyThrows(IOException.class)
	public void testMoveToNewPathWithNotExistNewFilePath() {
		final File oldFilePath = new File("/old");
		oldFilePath.mkdir();

		final File newFilePath = new File("/new");
		newFilePath.mkdir();
		if (newFilePath.exists()) {
			newFilePath.delete();
		}

		final String newFileName = "new.txt";
		final String oldFileName = "old.txt";
		final File oldFile = new File(oldFilePath, oldFileName);
		oldFile.createNewFile();
		final File newFile = new File(newFilePath, newFileName);
		newFile.createNewFile();
		if (newFile.exists()) {
			newFile.delete();
		}

		Assert.assertTrue(bizImpl.moveToNewPath("/old", "/new/new.txt", oldFileName, newFileName));
	}

	/**
	 * 不存在的文件的拷贝
	 */
	@Test
	public void testMoveToNewPathWithNotExistOldFile() {
		final File oldFile = new File("/old/old.txt");
		if (oldFile.exists()) {
			oldFile.delete();
		}

		final String newFileName = "new.txt";
		final String oldFileName = "old.txt";
		Assert.assertFalse(bizImpl.moveToNewPath("/old/old.txt", "/new/new.txt", oldFileName, newFileName));
	}
}
