package com.yueny.demo.capture.comp;

/**
 * 文件标记
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年1月7日 下午3:41:31
 *
 */
public interface IFileStamp {
	/**
	 * 判断文件是否存在
	 *
	 * @param filePath
	 *            文件路径
	 * @return boolean
	 */
	boolean checkFileExist(final String filePath);
}
