package com.yueny.demo.capture.demo.cardbin;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yueny.demo.capture.BaseSevice;
import com.yueny.demo.capture.core.IImportFilesService;
import com.yueny.demo.capture.enums.ImportDataType;
import com.yueny.demo.capture.model.config.ImportConfig;
import com.yueny.demo.capture.model.data.ImportCellDataBo;
import com.yueny.demo.capture.model.data.ImportSheetDataBo;
import com.yueny.demo.capture.model.data.RowColOrderBo;
import com.yueny.demo.capture.model.format.ImportFormatterBo;
import com.yueny.demo.capture.read.IFileReaderService;
import com.yueny.rapid.lang.util.DigitalUtil;
import com.yueny.rapid.lang.util.StringUtil;

/**
 * 文件读取
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年1月7日 下午3:38:51
 *
 */
@Service
public class CardBinServiceImpl extends BaseSevice implements ICardBinService {
	public interface IAssemblyLine {
		ImportFormatterBo line(List<String> columnRule);
	}

	@Autowired
	private IFileReaderService fileReaderService;
	@Autowired
	private IImportFilesService importFilesService;

	@Override
	public Map<String, List<String>> sqlWithFile(final String importFilePath) {
		final Map<String, List<String>> sqlDataMaps = Maps.newHashMap();

		try {
			final ImportConfig importConfig = new ImportConfig(importFilePath);
			importConfig.setIgnoreLines(4);
			importConfig.setFormatters(
					assemblyFormatterRule("/tfs/rule/chinapay-2016-card-bin-import-for-sql.rule", new IAssemblyLine() {
						@Override
						public ImportFormatterBo line(final List<String> columnRule) {
							// columnRule eg： ATM|ATM_SUPPORT|3|NUMBER|√

							final ImportFormatterBo fo = new ImportFormatterBo();
							fo.setTitleName(columnRule.get(0));// 表头名(titleName)
							fo.setFieldName(columnRule.get(1));// 属性名(fieldName)
							fo.setOrderNo(Integer.parseInt(columnRule.get(2)));// 顺序(orderNo)
							fo.setDataType(ImportDataType.valueOf(columnRule.get(3)));// 导入属性的数据类型(dataType)
							fo.setExample(columnRule.get(4));// 示例表头名(example)

							return fo;
						}
					}));

			final List<ImportSheetDataBo> sheetDataList = importFilesService.importFile(importConfig);

			if (CollectionUtils.isEmpty(sheetDataList)) {
				return Collections.emptyMap();
			}

			for (final ImportSheetDataBo sheetDataBo : sheetDataList) {
				final ImportCellDataBo cellData = sheetDataBo.getCellDataBo();

				final String sqlTemplete = sqlTemplete(cellData.getFieldOrders());

				final List<String> sqls = Lists.newArrayList();
				for (final Map<String, Object> map : cellData.getFieldDataList()) {
					String sql = sqlTemplete;

					final List<Object> args = Lists.newArrayList();
					for (final RowColOrderBo field : cellData.getFieldOrders()) {
						Object fieldValue = map.get(field.getFieldName());
						if (field.getDataType() == ImportDataType.NUMBER) {
							if (!DigitalUtil.isDigital(fieldValue.toString())) {
								if (StringUtils.equals("√", fieldValue.toString())) {
									fieldValue = 1;
								} else {
									fieldValue = 0;
								}
							} else {
								fieldValue = Long.valueOf(fieldValue.toString());
							}
						} else if (field.getDataType() == ImportDataType.STRING) {
							fieldValue = fieldValue.toString();
						}

						sql = StringUtils.replace(sql, "%" + field.getFieldName() + "%", fieldValue.toString());
						args.add(fieldValue);
					}

					sqls.add(sql);
				}
				sqlDataMaps.put(sheetDataBo.getSheetName(), sqls);
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return sqlDataMaps;
	}

	@Override
	public boolean sqlWithFile(final String importFilePath, final String sqlFilePath) {
		final ImportConfig importConfig = new ImportConfig(importFilePath);
		importConfig.setIgnoreLines(4);

		final List<ImportSheetDataBo> sheetDataList = importFilesService.importFile(importConfig);
		System.out.println(sheetDataList);

		if (CollectionUtils.isEmpty(sheetDataList)) {
			return false;
		}

		for (final ImportSheetDataBo sheetDataBo : sheetDataList) {
			// 生成文件 sheetName
			// .
		}
		return false;
	}

	private List<ImportFormatterBo> assemblyFormatterRule(final String ruleFile, final IAssemblyLine assemblyLine) {
		final List<ImportFormatterBo> importFormatterRules = Lists.newArrayList();

		final List<String> rules = fileReaderService.readLines(ruleFile);
		for (final String rule : rules) {
			if (StringUtil.isEmpty(rule) || StringUtil.startWith(rule, "#", false)) {
				continue;
			}

			// eg： ATM|ATM_SUPPORT|3|NUMBER|√
			final List<String> columnRule = Splitter.on("|").splitToList(rule);
			importFormatterRules.add(assemblyLine.line(columnRule));
		}
		return importFormatterRules;
	}

	private String sqlTemplete(final List<RowColOrderBo> rowColsWeight) {
		final StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO `cardBinConfig` ");
		sb.append("(");

		// 表字段组装
		final List<String> fcs = Lists.newArrayListWithCapacity(rowColsWeight.size());
		for (final RowColOrderBo rowColOrderBo : rowColsWeight) {
			fcs.add("`" + rowColOrderBo.getFieldName() + "`");
		}
		fcs.add("createDate");
		fcs.add("modifyDate");
		sb.append(Joiner.on(", ").join(fcs));

		sb.append(") ");

		sb.append("VALUES (");
		// 将rowColsWeight生成相应占位符
		final List<String> lps = Lists.newArrayListWithCapacity(rowColsWeight.size());
		for (final RowColOrderBo rowColOrderBo : rowColsWeight) {
			// %s 字符串类型; %b 布尔类型; %d 整数类型（十进制）
			if (rowColOrderBo.getDataType() == ImportDataType.STRING) {
				lps.add("'%" + rowColOrderBo.getFieldName() + "%'");
			} else if (rowColOrderBo.getDataType() == ImportDataType.NUMBER) {
				lps.add("%" + rowColOrderBo.getFieldName() + "%");
			} else if (rowColOrderBo.getDataType() == ImportDataType.DATE) {
				lps.add("'%s'");
			}
		}
		lps.add("now()");
		lps.add("now()");
		final String values = Joiner.on(", ").join(lps);
		// 表字段新增数据占位符
		sb.append(values);
		sb.append(");");

		return sb.toString();
	}

}
