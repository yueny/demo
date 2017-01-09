package com.yueny.demo.capture.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ImportModel {
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
