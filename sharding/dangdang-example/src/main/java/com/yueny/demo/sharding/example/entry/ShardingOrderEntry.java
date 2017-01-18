package com.yueny.demo.sharding.example.entry;

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
public class ShardingOrderEntry implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 3512287530374354180L;
	/**
	 * 建立时间<br>
	 * 包含建立时间和毫秒戳
	 */
	private Timestamp createTime;
	/** 主键 */
	private long id;
	/** 订单号 */
	private String orderId;
	/** 类型, ENUM('Y','N') */
	private String type;
	/**
	 * 更新时间<br>
	 * 包含更新时间和毫秒戳
	 */
	private Timestamp updateTime;
}
