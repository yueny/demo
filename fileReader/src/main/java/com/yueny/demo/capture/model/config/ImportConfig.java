package com.yueny.demo.capture.model.config;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 导入配置
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年1月9日 上午10:43:39
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ImportConfig {
	/**
	 * 忽略行数，默认忽略一行
	 */
	private Integer ignoreLines = 1;
	/**
	 * 导入的文件
	 */
	final String filePath;
}
