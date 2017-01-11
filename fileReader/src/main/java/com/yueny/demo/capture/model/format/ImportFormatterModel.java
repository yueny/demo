package com.yueny.demo.capture.model.format;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import com.yueny.demo.capture.model.data.RowColOrderBo;

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
public class ImportFormatterModel {
	// 标题行的字段序列,按模板顺序, rowKey is 序号顺序； columnKey is 排序的属性名fieldName
	private final Table<Integer, String, ImportFormatterBo> fieldRowColsOrder = TreeBasedTable.create();// HashBasedTable.create();
	/** 导入模板规则 */
	private final List<ImportFormatterBo> formatters;

	public ImportFormatterModel(final List<ImportFormatterBo> formatters) {
		this.formatters = formatters;

		fieldRowColsOrder.clear();

		for (final ImportFormatterBo format : formatters) {
			if (fieldRowColsOrder.containsRow(format.getOrderNo())) {
				continue;
			}
			fieldRowColsOrder.put(format.getOrderNo(), format.getFieldName(), format);
		}
	}

	/**
	 * 获取属性名字段,无模板顺序, key 为titleName
	 */
	public Map<String, ImportFormatterBo> getByTitleName() {
		final Map<String, ImportFormatterBo> maps = Maps.newHashMap();

		for (final Map.Entry<Integer, Map<String, ImportFormatterBo>> entry : fieldRowColsOrder.rowMap().entrySet()) {
			for (final Map.Entry<String, ImportFormatterBo> et : entry.getValue().entrySet()) {
				final ImportFormatterBo fo = et.getValue();
				maps.put(fo.getTitleName(), fo);
			}
		}
		return maps;
	}

	/**
	 * 获取有序的属性名字段,按模板顺序
	 */
	public List<RowColOrderBo> getFieldOrders() {
		final List<RowColOrderBo> lists = Lists.newArrayList();

		for (final Map.Entry<Integer, Map<String, ImportFormatterBo>> entry : fieldRowColsOrder.rowMap().entrySet()) {
			for (final Map.Entry<String, ImportFormatterBo> et : entry.getValue().entrySet()) {
				final RowColOrderBo rco = new RowColOrderBo(et.getValue().getDataType(), et.getKey(),
						et.getValue().getOrderNo(), et.getValue().getTitleName());
				lists.add(rco);
			}
		}
		return lists;
	}
}
