package com.yueny.demo.capture.model.format;

import com.yueny.demo.capture.model.data.RowColOrderBo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 文件导入格式对象
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年1月7日 下午10:43:32
 *
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class ImportFormatterBo extends RowColOrderBo {
	/** 示例表头名 */
	@Getter
	@Setter
	private String example;

	// /** 属性名 */
	// private String fieldName;
	//// /** 导入类型 */
	//// private String importType;
	// /** 顺序 */
	// private Integer orderNo;
	// /** 表头名 */
	// private String titleName;

}
