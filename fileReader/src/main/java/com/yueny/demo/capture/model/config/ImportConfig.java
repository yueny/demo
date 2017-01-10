package com.yueny.demo.capture.model.config;

import java.util.List;

import com.yueny.demo.capture.model.format.ImportFormatterBo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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
@AllArgsConstructor
@RequiredArgsConstructor
public class ImportConfig {
	/**
	 * 导入的文件
	 */
	@NonNull
	private String filePath;
	/**
	 * 导入模板规则
	 */
	// @NonNull
	private List<ImportFormatterBo> formatters;
	/**
	 * 忽略行数，默认忽略一行
	 */
	private Integer ignoreLines = 1;
}
