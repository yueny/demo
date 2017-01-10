package com.yueny.demo.capture.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.yueny.rapid.lang.util.StringUtil;
import com.yueny.rapid.lang.util.io.ResourcesLoader;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年1月9日 上午11:38:11
 *
 */
public class FilefixUtil {
	/**
	 * 得到文件
	 *
	 * @param absoluteFilePath
	 *            文件路径
	 */
	public static File getFile(final String absoluteFilePath) {
		File file = null;
		try {
			file = ResourcesLoader.getResourceAsFile(absoluteFilePath);
		} catch (final IOException e) {
			// ignore
		}

		if (file == null) {
			file = new File(absoluteFilePath);
		}

		return file;
	}

	/**
	 * 得到文件输入流
	 *
	 * @param absoluteFilePath
	 *            文件路径
	 */
	public static InputStream getInputStream(final String absoluteFilePath) {
		InputStream is = null;
		try {
			is = ResourcesLoader.getResourceAsStream(absoluteFilePath);
		} catch (final IOException e) {
			// ignore
		}

		if (is == null) {
			try {
				is = new FileInputStream(absoluteFilePath);
			} catch (final FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		return is;
	}

	/**
	 * get postfix of the path
	 */
	public static String getPostfix(final String path) {
		if (StringUtil.isEmpty(path)) {
			return null;
		}

		if (path.contains(".")) {
			final String fileSuffix = path.substring(path.lastIndexOf(".") + 1, path.length());
			return fileSuffix;
		}
		return null;
	}
}
