package com.yueny.demo.capture.read.line;

import java.util.List;
import java.util.Map;

import com.yueny.demo.capture.model.format.ImportFormatterBo;

/**
 * 抽取一行数据
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年1月7日 下午10:52:23
 *
 */
public interface IImportLineExtractor {
	/**
	 * 抽取一行数据
	 *
	 * @param importFormatterBoList
	 *            提取的格式
	 * @param lineNumber
	 *            内容行数
	 * @param stringLine
	 *            待抽取的内容行
	 * @return 完成抽取的数据映射
	 */
	Map<String, Object> extractLine(final List<ImportFormatterBo> importFormatterBoList, final String stringLine,
			final int lineNumber);
}
