package com.yueny.demo.capture.core;

import com.yueny.demo.capture.model.config.ImportConfig;

/**
 * 文件导入入口
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年1月7日 下午10:21:17
 *
 */
public interface IImportFilesService {
	/**
	 * 导入文件中的数据， 忽略第一行
	 *
	 * @param importConfig
	 *            导入配置
	 * @return true or false
	 */
	boolean importFile(ImportConfig importConfig);
}
