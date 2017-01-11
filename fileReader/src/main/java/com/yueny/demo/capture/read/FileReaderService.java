package com.yueny.demo.capture.read;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.yueny.demo.capture.BaseSevice;
import com.yueny.demo.capture.util.FilefixUtil;
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
public class FileReaderService extends BaseSevice implements IFileReaderService {
	private static final String CHARSET_ENCODE = "UTF-8";

	@Override
	public InputStream read(final String filePath) {
		return FilefixUtil.getInputStream(filePath);
	}

	@Override
	public List<String> readLines(final String filePath) {
		// Files.lines(Paths.get("D:\\jd.txt"),
		// StandardCharsets.UTF_8).forEach(System.out::println);

		// Files.readAllLines(path, cs);

		InputStream is = null;
		LineNumberReader lineNumberReader = null;
		try {
			is = FilefixUtil.getInputStream(filePath);

			lineNumberReader = new LineNumberReader(new InputStreamReader(is, CHARSET_ENCODE));
		} catch (final UnsupportedEncodingException e) {
			logger.error("文件转码异常", e);
			e.printStackTrace();
		} finally {
			// .
		}

		final List<String> lists = Lists.newArrayList();
		try {
			while (true) {
				final String stringLine = lineNumberReader.readLine();
				// 空 null
				if (stringLine == null) {
					break;
				}
				// 注释
				if (StringUtil.startWith(stringLine, "#", false)) {
					continue;
				}
				// 空行"" or " "
				if (StringUtil.isBlank(stringLine)) {
					continue;
				}

				lists.add(stringLine);
			}
		} catch (final IOException e) {
			logger.error("读取导入文件数据行发生错误");
			e.printStackTrace();
		} finally {
			try {
				if (lineNumberReader != null) {
					lineNumberReader.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (final IOException e) {
				logger.error(e.getMessage(), e);
			}
		}

		return lists;
	}

}
