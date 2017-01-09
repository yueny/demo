package com.yueny.demo.capture.read;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.yueny.demo.capture.BaseSevice;
import com.yueny.rapid.lang.util.io.ResourcesLoader;

/**
 * 文件读取
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年1月7日 下午3:38:51
 *
 */
@Service
public class FileReaderService extends BaseSevice implements IFileReaderService {
	private static final String CHARSET_ENCODE = "UTF-8";

	@Override
	public LineNumberReader getLineNumberReader(final String absoluteFilePath) {
		try {
			final InputStream is = getInputStream(absoluteFilePath);

			return new LineNumberReader(new InputStreamReader(is, CHARSET_ENCODE));
		} catch (final UnsupportedEncodingException e) {
			logger.error("文件转码异常", e);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PrintWriter getPrintWriter(final File file) {
		try {
			return new PrintWriter(file, CHARSET_ENCODE);
		} catch (final FileNotFoundException e) {
			logger.error("文件或文件目录不存在");
			e.printStackTrace();
		} catch (final UnsupportedEncodingException e) {
			logger.error("文件或文件目录不存在");
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<String> readLines(final String filePath) {
		// TODO Auto-generated method stub
		return null;
	}

	private InputStream getInputStream(final String absoluteFilePath) {
		InputStream is = null;
		try {
			is = ResourcesLoader.getResourceAsStream(absoluteFilePath);
		} catch (final IOException e) {
			// ignore
			if (logger.isErrorEnabled()) {
				// logger.error("读取文件异常", e);
				// e.printStackTrace();
			}
		}

		if (is == null) {
			try {
				is = new FileInputStream(absoluteFilePath);
			} catch (final FileNotFoundException e) {
				if (logger.isErrorEnabled()) {
					logger.error("读取文件异常", e);
					e.printStackTrace();
				}
			}
		}

		return is;
	}

}
