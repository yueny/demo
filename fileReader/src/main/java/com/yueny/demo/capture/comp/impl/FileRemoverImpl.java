package com.yueny.demo.capture.comp.impl;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

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
