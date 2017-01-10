package com.yueny.demo.capture.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yueny.demo.capture.BaseSevice;
import com.yueny.demo.capture.model.ImportFileBo;
import com.yueny.demo.capture.model.config.ImportConfig;
import com.yueny.demo.capture.model.data.ImportSheetDataBo;
import com.yueny.demo.capture.model.format.ImportFormatterBo;
import com.yueny.demo.capture.read.IFileReaderService;
import com.yueny.demo.capture.read.line.IImportLineExtractor;
import com.yueny.demo.capture.util.FilefixUtil;
import com.yueny.demo.capture.util.XlsUtil;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年1月7日 下午10:23:18
 *
 */
@Service
public class ImportFilesServiceImpl extends BaseSevice implements IImportFilesService {
	@Autowired
	private IImportLineExtractor lineExtractor;
	@Autowired
	private IFileReaderService readerService;

	@Override
	public List<ImportSheetDataBo> importFile(final ImportConfig importConfig) {
		final ImportFileBo importModel = assemblyModel(importConfig);
		if (importModel == null) {
			return null;
		}

		if (importModel.getFileType().equals("xls")) {
			final List<ImportSheetDataBo> sheetDataList = XlsUtil.readXls(importConfig);

			return sheetDataList;
		}
		if (importModel.getFileType().equals("xsls")) {
			final List<ImportSheetDataBo> sheetDataList = XlsUtil.readXls(importConfig);

			return sheetDataList;
		}
		if (importModel.getFileType().equals("cvs")) {
			final List<ImportSheetDataBo> sheetDataList = XlsUtil.readXls(importConfig);

			return sheetDataList;
		}

		// final LineNumberReader lineNumberReader =
		// readerService.getLineNumberReader(importModel.getOriginFilePath());
		// Assert.notNull(lineNumberReader, "临时文件无法获取");
		//
		// final List<ImportFormatterBo> importFormatterBoList = null;
		// try {
		// checkHeadLine(importFormatterBoList, lineNumberReader);
		// disposeIngoreLine(lineNumberReader, importConfig.getIgnoreLines());
		//
		// disposeDataLine(importFormatterBoList, lineNumberReader);
		// } catch (final IOException e) {
		// e.printStackTrace();
		// } finally {
		// try {
		// lineNumberReader.close();
		// } catch (final IOException e) {
		// logger.error("关闭lineNumberReader异常", e);
		// return false;
		// }
		//
		// // ExportImportUtils.deleteFile(importBo.getTempFilePath());
		// }

		return null;
	}

	private ImportFileBo assemblyModel(final ImportConfig importConfig) {
		final String originFilePath = importConfig.getFilePath();

		// 获取导入文件类型
		final String fileSuffix = FilefixUtil.getPostfix(originFilePath);

		final ImportFileBo importModel = new ImportFileBo();
		importModel.setOriginFilePath(originFilePath);
		importModel.setFileSuffix(fileSuffix);
		importModel.setFileType(fileSuffix);

		final File f = FilefixUtil.getFile(originFilePath);
		if (!f.exists() || (!f.isFile())) {
			logger.warn("file doesn't exist or is not a file");
			return null;
		}

		FileChannel fc = null;
		try {
			final FileInputStream fis = new FileInputStream(f);
			fc = fis.getChannel();

			importModel.setFileName(f.getName());
			importModel.setFileSize(fc.size());

			try {
				fis.close();
			} catch (final IOException e) {
				logger.error("异常！", e);
			}
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (fc != null) {
				try {
					fc.close();
				} catch (final IOException e) {
					logger.error("异常！", e);
				}
			}
		}

		return importModel;
	}

	/**
	 * 验证表头行
	 *
	 * @param 导入格式列表
	 *            （取其表头列）
	 * @param lineNumberReader
	 *            lineNumberReader
	 */
	private void checkHeadLine(final List<ImportFormatterBo> formats, final LineNumberReader lineNumberReader) {
		if (CollectionUtils.isEmpty(formats)) {
			return;
		}

		// final List<String> headCells = extractHeadCell(lineNumberReader,
		// formats.size());
		//
		// @SuppressWarnings("unchecked")
		// final Comparator<BaseBo> orderNoComparator = new
		// PropertyComparator("orderNo", true, true);
		// Collections.sort(formats, orderNoComparator);
		//
		// for (int i = 0; i < headCells.size(); i++) {
		// final String titleName = formats.get(i).getTitleName();
		// if (!StringUtils.equals(titleName, headCells.get(i))) {
		// throw new
		// RiskBusinessException(ErrorType.IMPORT_TITLE_READ_EXCEPTION,
		// "表头第" + (i + 1) + "列和模板不匹配，要求为<" + titleName + ">，输入值为<" +
		// headCells.get(i) + ">请核对模板");
		// }
		// }
	}

	/**
	 * 处理数据行
	 *
	 * @param importFormatterBoList
	 *            导入格式列表（取其属性列）
	 * @param lineNumberReader
	 *            lineNumberReader
	 * @throws IOException
	 */
	private Map<String, Object> disposeDataLine(final List<ImportFormatterBo> importFormatterBoList,
			final LineNumberReader lineNumberReader) throws IOException {
		try {
			while (true) {
				final String stringLine = lineNumberReader.readLine();
				if (stringLine == null) {
					break;
				}

				final Map<String, Object> lineData = lineExtractor.extractLine(importFormatterBoList, stringLine,
						lineNumberReader.getLineNumber());
				return lineData;
			}
		} catch (final IOException e) {
			logger.error("读取导入文件数据行发生错误");
			throw e;
		}
		return null;
	}

	/**
	 * 处理忽略的表头行
	 *
	 * @param lineNumberReader
	 */
	private void disposeIngoreLine(final LineNumberReader lineNumberReader, final Integer ignoreLines) {
		try {
			for (int i = 1; i < ignoreLines; i++) {
				lineNumberReader.readLine();
			}
		} catch (final IOException e) {
			logger.error("读取导入文件表头示例行发生错误", e);
		}
	}
}
