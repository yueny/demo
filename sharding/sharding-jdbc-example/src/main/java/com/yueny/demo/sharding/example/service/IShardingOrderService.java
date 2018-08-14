package com.yueny.demo.sharding.example.service;

import java.util.List;

import com.yueny.demo.sharding.example.bo.ShardingOrderBo;
import com.yueny.rapid.lang.util.enums.YesNoType;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月5日 上午9:56:50
 *
 */
public interface IShardingOrderService {
	List<ShardingOrderBo> queryAll();

	ShardingOrderBo queryByOrderId(String orderId);

	List<ShardingOrderBo> queryByType(YesNoType type);

	/**
	 * @return
	 */
	ShardingOrderBo queryByUserId(Long userId);

	List<ShardingOrderBo> queryByUserType(Long userId, YesNoType type);

	Long queryCount();
}
