package com.yueny.demo.sharding.example.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yueny.demo.sharding.example.bo.ShardingOrderBo;
import com.yueny.demo.sharding.example.dao.mapper.IShardingOrderMapper;
import com.yueny.demo.sharding.example.entry.ShardingOrderEntry;
import com.yueny.demo.sharding.example.service.BaseSevice;
import com.yueny.demo.sharding.example.service.IShardingOrderService;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月5日 上午9:57:38
 *
 */
@Service
public class ShardingOrderServiceImpl extends BaseSevice implements IShardingOrderService {
	@Autowired
	private IShardingOrderMapper shardingOrderMapper;

	@Override
	public List<ShardingOrderBo> queryAll() {
		final List<ShardingOrderEntry> entrys = shardingOrderMapper.selectAll();

		if (CollectionUtils.isEmpty(entrys)) {
			return Collections.emptyList();
		}

		return map(entrys, ShardingOrderBo.class);
	}

}
