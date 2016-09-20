package com.yueny.demo.hystrix.entry;

import com.yueny.kapo.api.annnotation.EntryPk;
import com.yueny.kapo.api.pojo.instance.BaseEntry;

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
public class ModifyDemoEntry extends BaseEntry {
	/** 描述 */
	private String desc;
	/** 事件数据 */
	private String eventData;
	/** 主键 */
	@EntryPk
	private long id;
	/** 名称 */
	private String name;
	/** 类型 */
	private String type;
}
