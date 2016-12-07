package com.yueny.demo.dynamic.scheduler.util;

import java.text.SimpleDateFormat;
import java.util.Date;

@Deprecated
public abstract class DateTimeUtils {

	/**
	 * 根据规则格式化时间
	 *
	 * @Title: fomat
	 * @Description: TODO
	 * @param date
	 * @param pattern
	 * @return
	 * @return: String
	 */
	public static String fomat(final String date, final String pattern) {

		final SimpleDateFormat format = new SimpleDateFormat(pattern);

		return format.format(new Date(Long.valueOf(date)));

	}
}
