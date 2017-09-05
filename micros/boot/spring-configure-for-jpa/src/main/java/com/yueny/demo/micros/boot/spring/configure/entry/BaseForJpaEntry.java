package com.yueny.demo.micros.boot.spring.configure.entry;

import java.sql.Timestamp;

import org.springframework.format.annotation.DateTimeFormat;

import com.yueny.rapid.lang.mask.builder.MaskToStringBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseForJpaEntry {
	/**
	 * 建立时间<br>
	 * 包含建立时间和毫秒戳
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp createTime;
	/** 修改人 */
	private String modifyUser;
	/**
	 * 更新时间<br>
	 * 包含更新时间和毫秒戳
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp updateTime;
	/** 版本号 */
	private int version;

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return MaskToStringBuilder.toString(this);
	}
}
