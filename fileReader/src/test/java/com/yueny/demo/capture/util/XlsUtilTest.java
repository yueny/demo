package com.yueny.demo.capture.util;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.yueny.demo.capture.model.config.ImportConfig;
import com.yueny.demo.capture.model.data.ImportSheetDataBo;
import com.yueny.demo.capture.model.format.ImportFormatterBo;
import com.yueny.demo.capture.read.FileReaderService;
import com.yueny.demo.capture.read.IFileReaderService;
import com.yueny.rapid.lang.util.StringUtil;

public class XlsUtilTest {
	private final IFileReaderService fileReaderService = new FileReaderService();

	@Test
	public void testImportFile() {
		final ImportConfig importConfig = new ImportConfig("/tfs/chinapay-2016-card-bin.xls");
		importConfig.setIgnoreLines(4);
		importConfig.setFormatters(assemblyFormatterRule());

		final List<ImportSheetDataBo> list = XlsUtil.readXls(importConfig);

		Assert.assertTrue(list.size() == 1);
	}

	private List<ImportFormatterBo> assemblyFormatterRule() {
		final List<ImportFormatterBo> importFormatterRules = Lists.newArrayList();

		final List<String> rules = fileReaderService.readLines("/tfs/rule/chinapay-card-bin.rule");
		for (final String rule : rules) {
			if (StringUtil.isEmpty(rule) || StringUtil.startWith(rule, "#", false)) {
				continue;
			}

			// eg： ATM|ATM_SUPPORT|3|NUMBER|√
			final List<String> columnRule = Splitter.on("|").splitToList(rule);

			final ImportFormatterBo fo = new ImportFormatterBo();
			fo.setTitleName(columnRule.get(0));
			fo.setFieldName(columnRule.get(1));
			fo.setOrderNo(Integer.parseInt(columnRule.get(2)));
			// fo.setDataType(columnRule.get(3));
			fo.setExample(columnRule.get(4));

			importFormatterRules.add(fo);
		}
		return importFormatterRules;
	}

}
