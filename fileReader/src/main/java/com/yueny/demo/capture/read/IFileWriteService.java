package com.yueny.demo.capture.read;

/**
 * 文件写
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年1月10日 下午12:43:12
 *
 */
public interface IFileWriteService {
	/**
	 * 追加文件
	 */
	boolean append(final String filePath);

	/**
	 * 写文件
	 */
	boolean write(final String filePath);
}
