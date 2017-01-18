package com.yueny.demo.sharding.example.dao.mapper;

import java.util.List;

import com.yueny.demo.sharding.example.entry.ShardingOrderEntry;

/**
 * 数据落地服务
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月10日 下午4:25:18
 */
public interface IShardingOrderMapper {
	List<ShardingOrderEntry> selectAll();
}
