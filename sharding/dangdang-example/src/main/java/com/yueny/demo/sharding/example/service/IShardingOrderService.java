package com.yueny.demo.sharding.example.service;

import java.util.List;

import com.yueny.demo.sharding.example.bo.ShardingOrderBo;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月5日 上午9:56:50
 *
 */
public interface IShardingOrderService {
	/**
	 * @return
	 */
	List<ShardingOrderBo> queryAll();

}
