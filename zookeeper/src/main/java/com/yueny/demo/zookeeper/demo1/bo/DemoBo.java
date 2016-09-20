package com.yueny.demo.zookeeper.demo1.bo;

import java.io.Serializable;

import lombok.Data;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月10日 下午6:03:01
 *
 */
@Data
public class DemoBo implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 8147737398400681614L;

	/**
	 * 实例的ID。
	 */
	private String id;

	/** 实例名称 */
	private String name;
}
