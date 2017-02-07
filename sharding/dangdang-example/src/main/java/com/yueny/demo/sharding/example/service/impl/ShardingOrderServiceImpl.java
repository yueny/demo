package com.yueny.demo.sharding.example.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yueny.demo.sharding.example.bo.ShardingOrderBo;
import com.yueny.demo.sharding.example.dao.mapper.IShardingOrderRepository;
import com.yueny.demo.sharding.example.entry.ShardingOrderEntry;
import com.yueny.demo.sharding.example.service.BaseSevice;
import com.yueny.demo.sharding.example.service.IShardingOrderService;
import com.yueny.rapid.lang.util.enums.YesNoType;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月5日 上午9:57:38
 *
 */
@Service
public class ShardingOrderServiceImpl extends BaseSevice implements IShardingOrderService {
	@Autowired
	private IShardingOrderRepository shardingOrderMapper;

	@Override
	public List<ShardingOrderBo> queryAll() {
		final List<ShardingOrderEntry> entrys = shardingOrderMapper.selectAll();

		if (CollectionUtils.isEmpty(entrys)) {
			return Collections.emptyList();
		}

		return map(entrys, ShardingOrderBo.class);
	}

	@Override
	public ShardingOrderBo queryByOrderId(final String orderId) {
		final ShardingOrderEntry entrys = shardingOrderMapper.selectByOrderId(orderId);

		if (entrys == null) {
			return null;
		}

		return map(entrys, ShardingOrderBo.class);
	}

	@Override
	public List<ShardingOrderBo> queryByType(final YesNoType type) {
		final List<ShardingOrderEntry> entrys = shardingOrderMapper.selectByType(type.name());

		if (CollectionUtils.isEmpty(entrys)) {
			return Collections.emptyList();
		}

		return map(entrys, ShardingOrderBo.class);
	}

	@Override
	public ShardingOrderBo queryByUserId(final Long userId) {
		final ShardingOrderEntry entrys = shardingOrderMapper.selectByUserId(userId);

		if (entrys == null) {
			return null;
		}

		return map(entrys, ShardingOrderBo.class);
	}

	@Override
	public List<ShardingOrderBo> queryByUserType(final Long userId, final YesNoType type) {
		final List<ShardingOrderEntry> entrys = shardingOrderMapper.selectByUserType(userId, type.name());

		if (CollectionUtils.isEmpty(entrys)) {
			return Collections.emptyList();
		}

		return map(entrys, ShardingOrderBo.class);
	}

	@Override
	// TODO 不能实现聚合
	public Long queryCount() {
		return shardingOrderMapper.selectCount();
	}

}
