package com.yueny.demo.capture.core;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.yueny.demo.capture.BaseBizTest;
import com.yueny.demo.capture.enums.ImportDataType;
import com.yueny.demo.capture.model.config.ImportConfig;
import com.yueny.demo.capture.model.data.ImportSheetDataBo;
import com.yueny.demo.capture.model.format.ImportFormatterBo;
import com.yueny.demo.capture.read.IFileReaderService;
import com.yueny.rapid.lang.util.StringUtil;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年1月9日 上午9:56:35
 *
 */
public class ImportFilesServiceImplTest extends BaseBizTest {
	@Autowired
	private IFileReaderService fileReaderService;
	@Autowired
	private IImportFilesService filesService;

	@Test
	public void testImportFile() {
		final ImportConfig importConfig = new ImportConfig("/tfs/chinapay-2016-card-bin.xls");
		importConfig.setIgnoreLines(4);
		importConfig.setFormatters(assemblyFormatterRule());

		final List<ImportSheetDataBo> rs = filesService.importFile(importConfig);
		Assert.assertTrue(rs != null);
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
			fo.setDataType(ImportDataType.valueOf(columnRule.get(3)));
			fo.setExample(columnRule.get(4));

			importFormatterRules.add(fo);
		}
		return importFormatterRules;
	}
}
