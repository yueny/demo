package com.yueny.demo.capture.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 导入文件
 * 
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年1月9日 下午1:21:42
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ImportFileBo {
	/** 文件名 */
	private String fileName;
	/** 文件大小 */
	private long fileSize;
	/** 文件后缀名, eg： xls */
	private String fileSuffix;
	/** 文件类型, eg： xls */
	private String fileType;
	/** 导入原文件路径 */
	private String originFilePath;
	/** 导入过程中生成的临时文件名 */
	private String tempFileName;
	/** 导入过程中生成的临时文件路径 */
	private String tempFilePath;
}
