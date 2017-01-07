package com.yueny.demo.capture.read;

import java.io.File;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月5日 上午9:56:50
 *
 */
public interface IFileReaderService {
	/**
	 * 获取LineNumberReader
	 *
	 * @param absoluteFilePath
	 *            绝对路径， 如 D:/adcfg.json or /tfs/select.sql
	 * @return LineNumberReader
	 */
	LineNumberReader getLineNumberReader(final String absoluteFilePath);

	/**
	 * 使用文件获取该文件的printewriter
	 *
	 * @param file
	 *            文件
	 * @return printwrite
	 */
	PrintWriter getPrintWriter(final File file);

	/**
	 * 读取所有的行
	 *
	 * @param filePath
	 *            文件路径
	 * @return 所有文件内容
	 */
	List<String> readLines(final String filePath);

}
