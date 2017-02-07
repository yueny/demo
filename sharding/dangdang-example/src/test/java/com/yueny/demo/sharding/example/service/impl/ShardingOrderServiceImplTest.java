/**
 *
 */
package com.yueny.demo.sharding.example.service.impl;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yueny.demo.sharding.example.bo.ShardingOrderBo;
import com.yueny.demo.sharding.example.service.BaseBizTest;
import com.yueny.demo.sharding.example.service.IShardingOrderService;
import com.yueny.rapid.lang.util.enums.YesNoType;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年8月5日 上午11:05:44
 *
 */
public class ShardingOrderServiceImplTest extends BaseBizTest {
	@Autowired
	private IShardingOrderService shardingOrderService;

	@Test
	public void testQueryAll() {
		// 查询所有表
		final List<ShardingOrderBo> lists = shardingOrderService.queryAll();

		Assert.assertTrue(lists.size() == 9);
	}

	@Test
	public void testQueryByOrderId() {
		// 查询所有表
		final ShardingOrderBo lists = shardingOrderService.queryByOrderId("1701060087302150");

		Assert.assertTrue(lists != null);
	}

	@Test
	public void testQueryByType() {
		// 查询所有表
		final List<ShardingOrderBo> lists = shardingOrderService.queryByType(YesNoType.N);

		Assert.assertTrue(lists.size() == 2);
	}

	@Test
	public void testQueryByUserId() {
		// 只查询 sharding_order_0
		final ShardingOrderBo lists = shardingOrderService.queryByUserId(6L);

		Assert.assertTrue(lists != null);
	}

	@Test
	public void testQueryByUserType() {
		// 只查询sharding_order_0， 和查询条件的顺序无关
		final List<ShardingOrderBo> lists = shardingOrderService.queryByUserType(6L, YesNoType.N);

		Assert.assertTrue(lists.size() == 1);
	}

	@Test
	public void testQueryCount() {
		// 查询所有表
		final Long count = shardingOrderService.queryCount();

		Assert.assertTrue(count == 9);
	}
}
