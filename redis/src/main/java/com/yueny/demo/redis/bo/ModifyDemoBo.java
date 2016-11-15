package com.yueny.demo.redis.bo;

import com.yueny.superclub.util.common.pojo.BaseBo;

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
public class ModifyDemoBo extends BaseBo {
	/**
	 *
	 */
	private static final long serialVersionUID = 1945994986391004513L;

	/** 描述 */
	private String desc;
	/** 事件数据 */
	private String eventData;
	/** 主键 */
	private long id;
	/** 名称 */
	private String name;
	/** 类型 */
	private String type;
}
