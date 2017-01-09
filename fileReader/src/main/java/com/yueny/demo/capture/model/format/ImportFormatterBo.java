package com.yueny.demo.capture.model.format;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件导入格式对象
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年1月7日 下午10:43:32
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ImportFormatterBo {
	/** 示例表头名 */
	private String example;
	/** 属性名 */
	private String fieldName;
	/** 导入类型 */
	private String importType;
	/** 顺序 */
	private long orderNo;
	/** 表头名 */
	private String titleName;
}
