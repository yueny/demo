package com.yueny.demo.redis.util;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.yueny.demo.redis.BaseBizTest;
import com.yueny.rapid.lang.util.UuidUtil;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年9月12日 下午10:20:52
 *
 */
public class RedisTest extends BaseBizTest {
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Test
	public void testRedis() {
		/* key/value */
		final String key = "aaaa";
		redisTemplate.opsForList().rightPush(key, UuidUtil.getSimpleUuid());
		Assert.assertTrue(redisTemplate.opsForList().size(key) == 1);
		redisTemplate.opsForList().leftPop(key);
		Assert.assertTrue(redisTemplate.opsForList().size(key) == 0);

		/* key/Set */
		final String listKey = "bbbb";
		final Set<String> members = redisTemplate.opsForSet().members(listKey);
		final Object[] result = new String[members.size()];
		int length = 0;
		for (final String t : members) {
			result[length] = t;
			length++;
		}
		redisTemplate.opsForSet().remove(listKey, result);

		final String value1 = UuidUtil.getSimpleUuid();
		redisTemplate.opsForSet().add(listKey, value1);

		Assert.assertTrue(redisTemplate.opsForSet().size(listKey) == 1);
		redisTemplate.opsForSet().add(listKey, UuidUtil.getSimpleUuid());
		Assert.assertTrue(redisTemplate.opsForSet().size(listKey) == 2);

		redisTemplate.opsForSet().remove(listKey, value1);
		Assert.assertTrue(redisTemplate.opsForSet().size(listKey) == 1);

		/* key/sort Set */
		final String sortSetKey = "ccccc";
		final String value2 = UuidUtil.getSimpleUuid();
		redisTemplate.opsForZSet().add(sortSetKey, value2, 10d);
		for (int i = 0; i < 10; i++) {
			sleep(500L);

			System.out.println(redisTemplate.opsForZSet().score(sortSetKey, value2));
		}
	}

	private void sleep(final Long sleepTime) {
		try {
			TimeUnit.MILLISECONDS.sleep(sleepTime);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		} finally {
			// .
		}
	}
}
