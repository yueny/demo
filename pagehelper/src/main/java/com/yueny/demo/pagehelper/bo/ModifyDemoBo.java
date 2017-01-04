package com.yueny.demo.pagehelper.bo;

import java.sql.Timestamp;

import com.yueny.superclub.api.pojo.instance.AbstractBo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月5日 上午10:46:33
 *
 */
@Data
@EqualsAndHashCode(callSuper = false, of = { "serialVersionUID" })
public class ModifyDemoBo extends AbstractBo {
	/**
	 *
	 */
	private static final long serialVersionUID = 1945994986391004513L;

	/** 创建时间 */
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
	/** 更新时间 */
	private Timestamp updateTime;
	/** 版本信息 */
	private int version;
}
