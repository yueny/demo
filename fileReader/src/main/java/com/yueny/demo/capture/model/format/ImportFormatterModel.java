package com.yueny.demo.capture.model.format;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 文件导入格式模型
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年1月10日 下午5:11:23
 *
 */
@ToString
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class ImportFormatterModel {
	// 标题行的字段序列， 有序存储, rowKey is 列顺序； columnKey is 排序的属性名fieldName
	private final Table<Integer, String, ImportFormatterBo> fieldRowColsOrder = TreeBasedTable.create();
	/** 导入模板规则 */
	private List<ImportFormatterBo> formatters;

	/**
	 * 获取有序的属性名字段
	 */
	public List<String> getFieldOrder() {
		final Set<String> sets = fieldRowColsOrder.columnKeySet();
		return Lists.newArrayList(sets);
	}

	/**
	 * 获取有序的属性名字段
	 */
	public Map<Integer, ImportFormatterBo> getOrderMap() {
		final Map<Integer, ImportFormatterBo> maps = Maps.newHashMap();

		for (final Map.Entry<String, Map<Integer, ImportFormatterBo>> entry : fieldRowColsOrder.columnMap()
				.entrySet()) {
			maps.putAll(entry.getValue());
		}
		return maps;
	}

	public void setFormatters(final List<ImportFormatterBo> formatters) {
		this.formatters = formatters;

		fieldRowColsOrder.clear();

		for (final ImportFormatterBo format : formatters) {
			if (fieldRowColsOrder.containsRow(format.getOrderNo())) {
				continue;
			}
			fieldRowColsOrder.put(format.getOrderNo(), format.getFieldName(), format);
		}
	}
}
