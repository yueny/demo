package com.yueny.demo.capture.comp;

/**
 * 文件移除服务
 * 
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年1月7日 下午4:18:03
 *
 */
public interface IFileRemover {
	/**
	 * remove all directories & files in the %listPath% which are created before
	 * the %date%
	 *
	 * @param listPath
	 *            the files' path
	 * @return result of remove
	 */
	boolean deleteFile(final String listPath);
}
