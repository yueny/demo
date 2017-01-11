package com.yueny.demo.capture.util;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.yueny.demo.capture.enums.ImportDataType;
import com.yueny.demo.capture.model.config.ImportConfig;
import com.yueny.demo.capture.model.data.ImportSheetDataBo;
import com.yueny.demo.capture.model.format.ImportFormatterBo;
import com.yueny.demo.capture.read.FileReaderService;
import com.yueny.demo.capture.read.IFileReaderService;
import com.yueny.rapid.lang.util.StringUtil;

public class XlsUtilTest {
	public interface IAssemblyLine {
		ImportFormatterBo line(List<String> columnRule);
	}

	private final IFileReaderService fileReaderService = new FileReaderService();

	@Test
	public void testImportFile16() {
		final ImportConfig importConfig = new ImportConfig("/tfs/chinapay-2016-card-bin.xls");
		importConfig.setIgnoreLines(4);
		importConfig.setFormatters(assemblyFormatterRule("/tfs/rule/chinapay-2016-card-bin.rule", new IAssemblyLine() {
			@Override
			public ImportFormatterBo line(final List<String> columnRule) {
				final ImportFormatterBo fo = new ImportFormatterBo();
				fo.setTitleName(columnRule.get(0));// 表头名(titleName)
				fo.setFieldName(columnRule.get(1));// 属性名(fieldName)
				fo.setOrderNo(Integer.parseInt(columnRule.get(2)));// 顺序(orderNo)
				fo.setDataType(ImportDataType.valueOf(columnRule.get(3)));// 导入属性的数据类型(dataType)
				fo.setExample(columnRule.get(4));// 示例表头名(example)

				return fo;
			}
		}));

		final List<ImportSheetDataBo> list = XlsUtil.readXls(importConfig);

		Assert.assertTrue(list.size() == 1);
	}

	@Test
	public void testImportFile6() {
		final ImportConfig importConfig = new ImportConfig("/tfs/demo-col3.xls");
		importConfig.setIgnoreLines(2);
		importConfig.setFormatters(assemblyFormatterRule("/tfs/rule/demo-col3.rule", new IAssemblyLine() {
			@Override
			public ImportFormatterBo line(final List<String> columnRule) {
				final ImportFormatterBo fo = new ImportFormatterBo();
				fo.setTitleName(columnRule.get(0));// 表头名(titleName)
				fo.setFieldName(columnRule.get(1));// 属性名(fieldName)
				fo.setOrderNo(Integer.parseInt(columnRule.get(2)));// 顺序(orderNo)
				// fo.setDataType(columnRule.get(3));//导入属性的数据类型(dataType)
				fo.setExample(columnRule.get(4));// 示例表头名(example)

				return fo;
			}
		}));

		final List<ImportSheetDataBo> list = XlsUtil.readXls(importConfig);

		Assert.assertTrue(list.size() == 1);
		Assert.assertTrue(list.get(0).getCellDataBo().getFieldDataList().size() == 4);
	}

	@Test
	public void testImportFile6WithWrongOrder() {
		final ImportConfig importConfig = new ImportConfig("/tfs/demo-col3.xls");
		importConfig.setIgnoreLines(2);
		importConfig.setFormatters(assemblyFormatterRule("/tfs/rule/demo-col3.rule", new IAssemblyLine() {
			@Override
			public ImportFormatterBo line(final List<String> columnRule) {
				final ImportFormatterBo fo = new ImportFormatterBo();
				fo.setTitleName(columnRule.get(0));// 表头名(titleName)
				fo.setFieldName(columnRule.get(1));// 属性名(fieldName)
				fo.setOrderNo(Integer.parseInt(columnRule.get(2)));// 顺序(orderNo)
				// fo.setDataType(columnRule.get(3));//导入属性的数据类型(dataType)
				fo.setExample(columnRule.get(4));// 示例表头名(example)

				return fo;
			}
		}));

		final List<ImportSheetDataBo> list = XlsUtil.readXls(importConfig);

		Assert.assertTrue(list.size() == 1);
		Assert.assertTrue(list.get(0).getCellDataBo().getFieldDataList().size() == 4);
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

}
