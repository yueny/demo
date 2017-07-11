package com.yueny.demo.common.example.entry;

import com.yueny.kapo.api.annnotation.EntryPk;
import com.yueny.kapo.api.pojo.instance.BaseEntry;
import com.yueny.rapid.lang.mask.builder.MaskToStringBuilder;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月5日 上午9:57:11
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DemoEntry extends BaseEntry {
	/** 描述 */
	private String desc;
	/** 主键 */
	@EntryPk
	private long id;
	/** 名称 */
	private String name;
	/** 类型 */
	private String type;

	@Override
	public long getPrimaryKey() {
		return id;
	}

	@Override
	@Deprecated
	public void setPrimaryKey(final long primaryKey) {
		// .
	}

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
