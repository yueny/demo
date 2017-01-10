package com.yueny.demo.capture.core;

import java.util.List;

import com.yueny.demo.capture.model.config.ImportConfig;
import com.yueny.demo.capture.model.data.ImportSheetDataBo;

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
	 * 获取导入文件中的数据， 忽略第N行
	 *
	 * @param importConfig
	 *            导入配置
	 * @return true or false
	 */
	List<ImportSheetDataBo> importFile(ImportConfig importConfig);
}
