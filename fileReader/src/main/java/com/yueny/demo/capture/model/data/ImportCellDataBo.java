package com.yueny.demo.capture.model.data;

import java.util.List;
import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 导入的单元（格）数据
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年1月9日 下午1:22:24
 *
 */
@ToString
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ImportCellDataBo {
	/**
	 * 数据行<br>
	 * 数据行列表, List中为每一行的数据，key: fieldName; value: 数据
	 */
	@Getter
	@Setter
	private List<Map<String, Object>> fieldDataList;
	/**
	 * 属性名有序列表
	 */
	@Getter
	@Setter
	private List<String> fieldNames;
	/**
	 * 标题行<br>
	 * 标题行列表, key:索引位置， 从1开始; value:该处的单元格的值
	 */
	@Getter
	@Setter
	private List<Map<Integer, String>> headerLine;
	/**
	 * 忽略行数，从1开始，原则上等于headerLine.size()
	 */
	@Getter
	@Setter
	private Integer ignoreLines;

}
