package com.yueny.demo.capture.demo.cardbin;

import java.util.Map;

/**
 * 卡bin服务
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年1月10日 下午12:46:06
 *
 */
public interface ICardBinService {
	/**
	 * 根据卡bin文件生成SQL
	 *
	 * @param filePath
	 *            文件路径
	 * @return key: 源文件名： value:生成的SQL
	 */
	Map<String, String> sqlWithFile(final String importFilePath);

	/**
	 * 根据卡bin文件生成SQL
	 *
	 * @param importFilePath
	 *            文件路径
	 * @param sqlFilePath
	 *            写入文件
	 * @return
	 */
	boolean sqlWithFile(final String importFilePath, final String sqlFilePath);
}
