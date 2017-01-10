package com.yueny.demo.capture.model.data;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * 导入的工作簿数据
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年1月9日 下午9:18:04
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@RequiredArgsConstructor
public class ImportSheetDataBo {
	/**
	 * 单元（格）数据
	 */
	private ImportCellDataBo cellDataBo;
	/**
	 * 单元簿名称
	 */
	@NonNull
	private String sheetName;
}
