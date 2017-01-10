package com.yueny.demo.capture.demo.cardbin;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yueny.demo.capture.BaseSevice;
import com.yueny.demo.capture.core.IImportFilesService;
import com.yueny.demo.capture.model.config.ImportConfig;
import com.yueny.demo.capture.model.data.ImportCellDataBo;
import com.yueny.demo.capture.model.data.ImportSheetDataBo;
import com.yueny.demo.capture.model.format.ImportFormatterBo;
import com.yueny.demo.capture.read.IFileReaderService;
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
	@Autowired
	private IFileReaderService fileReaderService;
	@Autowired
	private IImportFilesService importFilesService;

	@Override
	public Map<String, String> sqlWithFile(final String importFilePath) {
		final ImportConfig importConfig = new ImportConfig(importFilePath);
		importConfig.setIgnoreLines(4);
		importConfig.setFormatters(assemblyFormatterRule());

		final List<ImportSheetDataBo> sheetDataList = importFilesService.importFile(importConfig);
		System.out.println(sheetDataList);

		if (CollectionUtils.isEmpty(sheetDataList)) {
			return Collections.emptyMap();
		}

		final Map<String, String> sqlDataMaps = Maps.newHashMap();
		for (final ImportSheetDataBo sheetDataBo : sheetDataList) {
			final ImportCellDataBo cellData = sheetDataBo.getCellDataBo();

			final String sqlTemplete = sqlTemplete(cellData.getFieldNames());
			System.out.println(sqlTemplete);

			for (final Map<String, Object> map : cellData.getFieldDataList()) {
				/*
				 * eg： {CARD_BIN=623529, TRACK_START=2, CARD_NAME=中国银联移动支付标记化产品,
				 * ATM_SUPPORT=√, PRIMARY_ACCOUNT=623529xxxxxxxxxxxxx,
				 * PRIMARY_ACCOUNT_TRACK=2, CARD_TYPE=借记卡,
				 * ISSUING_BANK_LENGTH=6, TRACK=2, ISSUING_BANK_TRACK=2,
				 * PRIMARY_ACCOUNT_START=2, PRIMARY_ACCOUNT_LENGTH=19,
				 * TRACK_LENGTH=37, ISSUING_BANK_START=2, POS_SUPPORT=√,
				 * ADDITIONS= , BANK_NAME=中国银联支付标记(00010030)}
				 */

				// map.entrySet();

			}
			importConfig.getFormatters();

			final StringBuilder sb = new StringBuilder();
			// for (final ImportSheetDataBo importSheetDataBo :
			// cellData.getRowOrderColNamesList()) {
			//
			// }
			// sb.append(String.format(sqlTemplete, ""));
			sb.append("</br>");

			sqlDataMaps.put(sheetDataBo.getSheetName(), sb.toString());
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

	private String sqlTemplete(final List<String> rowColsWeight) {
		// delete from `CARD_BIN_DATA` where payGateCode='UM_PAY' and
		// requestType='PAY' and executeType='EXECUTE';
		final StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO `CARD_BIN_DATA` ");
		sb.append("(");
		// `payGateCode`,`bizDefineType`
		sb.append(Joiner.on(",").join(rowColsWeight));
		sb.append(") ");

		sb.append("VALUES (");
		sb.append("s%, s%, s%, s%, s%, s%, s%, s%, now(), now()");
		sb.append(");");

		return sb.toString();
	}

}
