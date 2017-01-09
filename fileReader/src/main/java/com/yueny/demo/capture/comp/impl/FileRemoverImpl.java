package com.yueny.demo.capture.comp.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.yueny.demo.capture.BaseSevice;
import com.yueny.demo.capture.comp.IFileRemover;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年1月7日 下午4:18:54
 *
 */
@Service
public class FileRemoverImpl extends BaseSevice implements IFileRemover {
	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.yueny.demo.capture.comp.IFileRemover#deleteFile(java.lang.String)
	 */
	@Override
	public boolean deleteFile(final String listPath) {
		final File dir = new File(listPath);
		for (final File file : getFileList(dir)) {
			// 前置检查
			// .

			if (!deleteDirectory(file)) {
				return false;
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.yueny.demo.capture.comp.IFileRemover#moveToNewPath(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean moveToNewPath(final String oldPath, final String newPath, final String oldFileName,
			final String newFileName) {
		final File oldFile = new File(oldPath + "/" + oldFileName);
		if (!oldFile.exists()) {
			return false;
		}

		boolean b = true;
		final File newFilePath = new File(newPath);
		if (!newFilePath.exists()) {
			b = newFilePath.mkdirs();
		}
		Assert.isTrue(b, "创建临时文件夹失败");

		final File newFile = new File(newPath + "/" + newFileName);
		if (!newFile.exists()) {
			try {
				b = newFile.createNewFile();
			} catch (final IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		Assert.isTrue(b, "创建临时文件失败");

		b = copyFile(oldFile, newFile);
		oldFile.delete();
		Assert.isTrue(b, "移动文件失败");

		return b;
	}

	/**
	 * @param oldFile
	 * @param newFile
	 * @return boolean
	 */
	private boolean copyFile(final File oldFile, final File newFile) {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(oldFile);
			os = new FileOutputStream(newFile);
			final byte[] buffer = new byte[10240];
			int b = 0;
			while ((b = is.read(buffer)) != -1) {
				os.write(buffer, 0, b);
			}
			return true;
		} catch (final FileNotFoundException e) {
			logger.error(e.getMessage(), e);
			return false;
		} catch (final IOException e) {
			logger.error(e.getMessage(), e);
			return false;
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.close();
				}
			} catch (final IOException e) {
				logger.error(e.getMessage(), e);
				return false;
			}
		}
	}

	/**
	 * 删除文件目录
	 *
	 * @param file
	 * @return
	 */
	private boolean deleteDirectory(final File file) {
		if (file.isFile()) {
			logger.info(file.getPath() + "不是文件目录,删除失败!");
			return false;
		}

		for (final File fileChild : getFileList(file)) {
			if (fileChild.delete()) {
				logger.info("文件" + fileChild.getPath() + "删除成功!");
			} else {
				logger.error("文件" + fileChild.getPath() + "删除失败!");
				return false;
			}
		}

		if (file.delete()) {
			logger.info("目录" + file.getPath() + "删除成功!");
			return true;
		}

		logger.error("目录" + file.getPath() + "删除失败!");
		return false;
	}

	/**
	 * 获取文件目录下的文件列表
	 *
	 * @param dir
	 *            文件
	 */
	private List<File> getFileList(final File dir) {
		return Arrays.asList(dir.listFiles());
	}

}
