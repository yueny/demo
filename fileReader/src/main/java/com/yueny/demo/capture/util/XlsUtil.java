package com.yueny.demo.capture.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.yueny.demo.capture.model.config.ImportConfig;
import com.yueny.demo.capture.model.data.ImportCellDataBo;
import com.yueny.demo.capture.model.data.ImportSheetDataBo;
import com.yueny.demo.capture.model.format.ImportFormatterBo;
import com.yueny.demo.capture.model.format.ImportFormatterModel;
import com.yueny.rapid.lang.util.StringUtil;

public final class XlsUtil {
	// TODO 当sheet有多个时， formatter模板不通用， 存在问题
	/**
	 * 导入xls操作 formatterModel
	 */
	public static List<ImportSheetDataBo> readXls(final ImportConfig importConfig) {
		return readXls(FilefixUtil.getInputStream(importConfig.getFilePath()), importConfig.getIgnoreLines(),
				new ImportFormatterModel(importConfig.getFormatters()));
	}

	/**
	 * 导入xls操作
	 *
	 * @param is
	 *            输入流
	 * @param ignoreLines
	 * @param formatters
	 *            导入模板规则
	 */
	public static List<ImportSheetDataBo> readXls(final InputStream is, int ignoreLines,
			final ImportFormatterModel formatterModel) {
		final List<ImportSheetDataBo> sheetDataList = Lists.newArrayList();

		final Map<String, ImportFormatterBo> titleNameMap = formatterModel.getByTitleName();

		HSSFWorkbook hssfWorkbook = null;
		try {
			hssfWorkbook = new HSSFWorkbook(is);

			// 循环工作表Sheet
			final int sheets = hssfWorkbook.getNumberOfSheets();
			for (int numSheet = 0; numSheet < sheets; numSheet++) {
				final HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
				if (hssfSheet == null) {
					continue;
				}

				/* 处理工作簿数据 */
				final ImportSheetDataBo sheetDataBo = new ImportSheetDataBo(hssfSheet.getSheetName());

				// 标题行列表, key:索引位置， 从1开始; value:该处的单元格的值
				final List<Map<Integer, String>> headerContext = Lists.newArrayList();
				final Map<Integer, String> headerLine = Maps.newHashMap();
				// 数据行列表, List中位每一行的数据，key: fieldName; value: 数据
				final List<Map<String, Object>> fieldDataList = Lists.newArrayList();

				// 得到最后列数，用于循环出数据内容
				final int lastRowNum = hssfSheet.getLastRowNum();
				if ((lastRowNum + 1) < ignoreLines) {
					ignoreLines = lastRowNum;
				}

				final ImportCellDataBo importDataBo = new ImportCellDataBo();
				importDataBo.setIgnoreLines(ignoreLines);
				importDataBo.setFieldOrders(formatterModel.getFieldOrders());

				final Table<Integer, Integer, String> tableHeader = HashBasedTable.create();
				// 获取合并行
				final List<CellRangeAddress> listCombineCell = getCombineCellWithHeader(hssfSheet, ignoreLines);
				/* header --> 循环行Row: 0-(ignoreLines-1) */
				for (int rowNum = 0; rowNum < ignoreLines; rowNum++) {
					final HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					if (hssfRow == null) {
						continue;
					}

					final Map<Integer, String> disposeHeaderLine = disposeHeaderLine(hssfRow, tableHeader,
							listCombineCell);
					headerContext.add(disposeHeaderLine);

					// TODO 暂定最后一行为模板
					if (rowNum == (ignoreLines - 1)) {
						headerLine.putAll(disposeHeaderLine);
					}
				}
				importDataBo.setHeaderContext(headerContext);

				/* body --> 循环行Row: ignoreLines-max */
				for (int rowNum = ignoreLines; rowNum <= lastRowNum; rowNum++) {
					final HSSFRow hssfRow = hssfSheet.getRow(rowNum);
					if (hssfRow == null) {
						continue;
					}

					// 一行数据， 循环列Cell
					final Map<String, Object> fieldRowData = Maps.newHashMap();
					for (int cellNum = 0; cellNum <= hssfRow.getLastCellNum(); cellNum++) {
						final HSSFCell hssfCell = hssfRow.getCell(cellNum);
						if (hssfCell == null) {
							continue;
						}

						// 第几列的数据， 则取第几列的header， 获取相应的模板
						final ImportFormatterBo format = titleNameMap.get(headerLine.get(cellNum));
						if (format == null) {
							continue;
						}
						final String dataKey = format.getFieldName();

						final String dataValue = getValue(hssfCell);
						// 去除换行符
						final String newDataValue = StringUtil.replaceBlank(dataValue);

						fieldRowData.put(dataKey, newDataValue);
					}

					fieldDataList.add(fieldRowData);
				}
				importDataBo.setFieldDataList(fieldDataList);

				sheetDataBo.setCellDataBo(importDataBo);
				sheetDataList.add(sheetDataBo);
			}

			return sheetDataList;
		} catch (final IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (hssfWorkbook != null) {
					hssfWorkbook.close();
				}
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}

		return Collections.emptyList();
	}

	/**
	 * 处理忽略的表头行
	 *
	 * @return key:索引位置， 从1开始; value:改处的单元格的值
	 */
	private static Map<Integer, String> disposeHeaderLine(final HSSFRow hssfRow,
			final Table<Integer, Integer, String> tableHeader, final List<CellRangeAddress> listCombineCell) {
		final Map<Integer, String> cellData = Maps.newHashMap();

		// 一行数据， 循环列Cell
		for (int cellNum = 0; cellNum <= hssfRow.getLastCellNum(); cellNum++) {
			final HSSFCell hssfCell = hssfRow.getCell(cellNum);
			if (hssfCell == null) {
				continue;
			}

			final boolean isMerge = isMergedRegion(listCombineCell, hssfRow.getRowNum(), hssfCell.getColumnIndex());

			String cellValue = null;
			if (isMerge) {
				cellValue = getMergedRegionValue(listCombineCell, tableHeader, hssfRow, hssfCell);
			} else {
				cellValue = getValue(hssfCell);
			}

			tableHeader.put(hssfCell.getAddress().getRow(), hssfCell.getAddress().getColumn(), cellValue);
			cellData.put(cellNum, cellValue);
		}

		// 处理重复的cellData， 并添加索引范围
		return cellData;
	}

	/**
	 * 合并单元格处理,获取合并行
	 *
	 * @param sheet
	 * @return List<CellRangeAddress>
	 */
	private static List<CellRangeAddress> getCombineCellWithHeader(final Sheet sheet, final int ignoreLines) {
		final List<CellRangeAddress> list = new ArrayList<CellRangeAddress>();
		// 获取所有合并过的单元格从0开始的（读取顺序为从上到下）
		final int sheetmergerCount = sheet.getNumMergedRegions();
		// 遍历合并单元格
		for (int i = 0; i < sheetmergerCount; i++) {
			// 获得合并单元格加入list中
			final CellRangeAddress ca = sheet.getMergedRegion(i);
			list.add(ca);
		}
		return list;
	}

	/**
	 * 取得指定的单元格的合并单元格对象
	 *
	 * @param listCombineCell
	 * @param row
	 *            行下标
	 * @param column
	 *            列下标
	 */
	private static CellRangeAddress getMergedRegion(final List<CellRangeAddress> listCombineCell, final int row,
			final int column) {
		for (final CellRangeAddress range : listCombineCell) {
			// 获得合并单元格的起始行, 结束行, 起始列, 结束列
			final int firstColumn = range.getFirstColumn();// index从0开始
			final int lastColumn = range.getLastColumn();
			final int firstRow = range.getFirstRow();
			final int lastRow = range.getLastRow();

			// if (row >= firstRow && row <= lastRow) {
			// if (column >= firstColumn && column <= lastColumn) {
			if ((row >= firstRow && row <= lastRow) && (column >= firstColumn && column <= lastColumn)) {
				return range;
			}

		}

		return null;
	}

	/**
	 * 获取合并单元格的值
	 *
	 * @param listCombineCell
	 * @param tableHeader
	 * @param currentRow
	 * @param currentCell
	 * @return
	 */
	private static String getMergedRegionValue(final List<CellRangeAddress> listCombineCell,
			final Table<Integer, Integer, String> tableHeader, final HSSFRow currentRow, final HSSFCell currentCell) {
		// 合并区域的第一个对象， 有数据， 则取之
		final String cellValue = getValue(currentCell);
		if (!StringUtil.isEmpty(cellValue)) {
			return cellValue;
		}

		// 如果是空， 返回合并单元格的第一个节点
		final CellRangeAddress range = getMergedRegion(listCombineCell, currentRow.getRowNum(),
				currentCell.getColumnIndex());

		if (range != null) {
			final int firstColumn = range.getFirstColumn();
			final int firstRow = range.getFirstRow();

			//// HSSFRow newDataRow=firstRow;
			// final HSSFCell newDataCell = currentRow.getCell(firstColumn);
			// return getValue(newDataCell);

			// or
			return tableHeader.get(firstRow, firstColumn);
		}

		return null;
	}

	/**
	 * 获取excel中单元格的值
	 */
	private static String getValue(final HSSFCell hssfCell) {
		if (hssfCell == null) {
			return "";
		}

		if (hssfCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(hssfCell.getBooleanCellValue());
		}
		if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
			return String.valueOf(hssfCell.getNumericCellValue());
		}
		if (hssfCell.getCellType() == HSSFCell.CELL_TYPE_FORMULA) {
			return hssfCell.getCellFormula();
		}

		// Cell.CELL_TYPE_STRING
		return String.valueOf(hssfCell.getStringCellValue());
	}

	/**
	 * 判断指定的单元格是否是合并单元格
	 *
	 * @param listCombineCell
	 * @param row
	 *            行下标
	 * @param column
	 *            列下标
	 */
	private static boolean isMergedRegion(final List<CellRangeAddress> listCombineCell, final int row,
			final int column) {
		return getMergedRegion(listCombineCell, row, column) != null;
	}

}
