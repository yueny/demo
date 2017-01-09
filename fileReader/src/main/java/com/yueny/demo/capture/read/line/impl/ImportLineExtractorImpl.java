package com.yueny.demo.capture.read.line.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.yueny.demo.capture.BaseSevice;
import com.yueny.demo.capture.model.format.ImportFormatterBo;
import com.yueny.demo.capture.read.line.IImportLineExtractor;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年1月7日 下午10:53:09
 *
 */
@Service
public class ImportLineExtractorImpl extends BaseSevice implements IImportLineExtractor {
	// csv默认分隔符
	private static final String SEPARATOR = ",";

	@Override
	public Map<String, Object> extractLine(final List<ImportFormatterBo> importFormatterBoList, final String stringLine,
			final int lineNumber) {
		String toExtract = stringLine;
		if (StringUtils.endsWith(toExtract, SEPARATOR)) {
			toExtract = StringUtils.substringBeforeLast(toExtract, SEPARATOR);
		}

		final String[] datas = StringUtils.splitPreserveAllTokens(toExtract, SEPARATOR);

		final Map<String, Object> dataMap = new HashMap<String, Object>();
		for (int row = 0; row < datas.length; row++) {
			final String dataValue = datas[row];

			final ImportFormatterBo importFormatterBo = importFormatterBoList.get(row);
			final String dataKey = importFormatterBo.getFieldName();
			dataMap.put(dataKey, dataValue);
		}
		return dataMap;
	}

}
