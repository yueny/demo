package com.yueny.demo.capture.read;

import java.io.InputStream;
import java.util.List;

/**
 * 文件读
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月5日 上午9:56:50
 *
 */
public interface IFileReaderService {
	/**
	 * 读取文件输入流
	 *
	 * @param filePath
	 *            文件路径
	 */
	InputStream read(final String filePath);

	/**
	 * 读取所有的行
	 *
	 * @param filePath
	 *            文件路径
	 * @return 所有文件内容
	 */
	List<String> readLines(final String filePath);
}
