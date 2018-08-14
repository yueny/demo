package com.yueny.demo.sharding.example.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.yueny.demo.sharding.example.entry.ShardingOrderEntry;

/**
 * 数据落地服务
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月10日 下午4:25:18
 */
public interface IShardingOrderRepository {
	List<ShardingOrderEntry> selectAll();

	@Select("SELECT * FROM SHARDING_ORDER WHERE ORDER_ID = #{orderId}")
	ShardingOrderEntry selectByOrderId(@Param(value = "orderId") String orderId);

	@Select("SELECT * FROM SHARDING_ORDER WHERE TYPE = #{type}")
	List<ShardingOrderEntry> selectByType(@Param("type") String type);

	ShardingOrderEntry selectByUserId(@Param(value = "userId") Long userId);

	@Select("SELECT * FROM SHARDING_ORDER WHERE USER_ID = #{userId} and TYPE = #{type}")
	List<ShardingOrderEntry> selectByUserType(@Param("userId") Long userId, @Param("type") String type);

	@Select("SELECT COUNT(*) FROM SHARDING_ORDER")
	Long selectCount();
}
