package com.yueny.demo.capture.comp.impl;

import java.io.File;

import org.springframework.stereotype.Service;

import com.yueny.demo.capture.BaseSevice;
import com.yueny.demo.capture.comp.IFileStamp;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年1月7日 下午3:42:06
 *
 */
@Service
public class FileStampImpl extends BaseSevice implements IFileStamp {

	@Override
	public boolean checkFileExist(final String filePath) {
		final File file = new File(filePath);
		return file.exists();
	}

}
