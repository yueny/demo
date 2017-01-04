package com.yueny.demo.pagehelper.entry;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

/**
 * @category just for test
 * @author <a href="mailto:yueny09@126.com"> 袁洋 2014年11月25日
 */
@Getter
@Setter
public class ModifyDemoEntry implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 3512287530374354180L;

	/**
	 * 建立时间<br>
	 * 包含建立时间和毫秒戳
	 */
	private Timestamp createTime;
	/** 描述 */
	private String desc;
	/** 事件数据 */
	private String eventData;
	/** 主键 */
	private long id;
	/** 修改人 */
	private String modifyUser;
	/** 名称 */
	private String name;
	/** 类型 */
	private String type;
	/**
	 * 更新时间<br>
	 * 包含更新时间和毫秒戳
	 */
	private Timestamp updateTime;
	/** 版本号 */
	private int version;
}
