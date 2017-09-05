/**
 *
 */
package com.yueny.demo.micros.boot.spring.configure.storager.cache;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yueny.rapid.lang.json.JsonUtil;
import com.yueny.rapid.lang.util.StringUtil;

/**
 * 缓存服务
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年11月17日 下午2:14:14
 * @since 1.5.3
 */
@Component
public class CacheService {
	/**
	 * Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(CacheService.class);

	@Autowired
	// private StringRedisTemplate stringRedisTemplate;
	private RedisTemplate<String, String> redisTemplate;

	final Gson gson = new Gson();

	/**
	 * 针对字符串结果的缓存处理<br>
	 * null不进行缓存， 发生异常不进行捕获， 不进行缓存<br>
	 * 通过JsonUtil操作<br>
	 *
	 * @param keys
	 *            缓存键值串
	 * @param handler
	 *            操作动作
	 * @param timeout
	 *            失效时间,秒
	 * @return
	 */
	public String process(final List<String> keys, final CacheDataHandler<String> handler, final Long timeout) {
		final String cacheKey = CacheKeyUtil.assmebledKeys(keys);

		return process(cacheKey, handler, timeout);
	}

	/**
	 * 针对简单结果类型的缓存处理<br>
	 * null不进行缓存， 发生异常不进行捕获， 不进行缓存<br>
	 * 通过JsonUtil操作<br>
	 *
	 * @param keys
	 *            缓存键值串
	 * @param handler
	 *            操作动作
	 * @param timeout
	 *            失效时间,秒
	 * @param valueType
	 *            结果类型， 如 String.class
	 * @return
	 */
	public <T> T process(final List<String> keys, final CacheDataHandler<T> handler, final Long timeout,
			final Class<T> valueType) {
		final String cacheKey = CacheKeyUtil.assmebledKeys(keys);

		return process(cacheKey, handler, timeout, valueType);
	}

	/**
	 * 针对字符串结果的缓存处理<br>
	 * null不进行缓存， 发生异常不进行捕获， 不进行缓存<br>
	 * 通过JsonUtil操作<br>
	 *
	 * @param cacheKey
	 *            缓存键值串, 调用此接口,请确保该key在全局唯一
	 * @param handler
	 *            操作动作
	 * @param timeout
	 *            失效时间,秒
	 * @return
	 */
	public String process(final String cacheKey, final CacheDataHandler<String> handler, final Long timeout) {
		return process(cacheKey, handler, timeout, String.class);
	}

	/**
	 * 针对简单结果类型的缓存处理<br>
	 * null不进行缓存， 发生异常不进行捕获， 不进行缓存<br>
	 * 通过JsonUtil操作<br>
	 *
	 * @param cacheKey
	 *            缓存key, 调用此接口,请确保该key在全局唯一
	 * @param timeout
	 *            失效时间,秒
	 * @param handler
	 * @return TODO 未验证
	 */
	public <T> T process(final String cacheKey, final CacheDataHandler<T> handler, final Long timeout,
			final Class<T> valueType) {
		if (redisTemplate == null) {
			return handler.caller();
		}

		final String ts = getValue(cacheKey);
		if (StringUtil.isNotEmpty(ts)) {
			final T t = JsonUtil.fromJson(ts, valueType);
			if (t != null) {
				return t;
			}
		}

		return callerForSet(cacheKey, handler, timeout, false);
	}

	/**
	 * 针对复杂结果类型的缓存处理<br>
	 * null不进行缓存， 发生异常不进行捕获， 不进行缓存<br>
	 * 通过GSON操作<br>
	 *
	 * @param cacheKey
	 *            缓存键值串, 调用此接口,请确保该key在全局唯一
	 * @param handler
	 *            操作动作
	 * @param timeout
	 *            失效时间,秒
	 * @param valueType
	 *            复杂结果类型，如new TypeToken<List<ModifyDemoForJpaEntry>>() {}
	 * @return
	 */
	public <T> T process(final String cacheKey, final CacheDataHandler<T> handler, final Long timeout,
			final TypeToken<T> valueType) {
		if (redisTemplate == null) {
			return handler.caller();
		}

		// TODO 没有办法解决缓存雪崩的问题
		final String ts = getValue(cacheKey);
		if (StringUtil.isNotEmpty(ts)) {
			final T t = gson.fromJson(ts, valueType.getType());
			if (t != null) {
				return t;
			}
		}

		return callerForSet(cacheKey, handler, timeout, true);
	}

	/**
	 * @param cacheKey
	 *            缓存键值串, 调用此接口,请确保该key在全局唯一
	 * @param handler
	 *            操作动作
	 * @param timeout
	 *            失效时间,秒
	 * @param isComplex
	 *            是否为复杂对象，如果为是/true，则通过GSON操作，否则false,简单类型通过JsonUtil操作
	 * @return
	 */
	private <T> T callerForSet(final String cacheKey, final CacheDataHandler<T> handler, final Long timeout,
			final boolean isComplex) {
		final T t = handler.caller();
		if (t == null) {
			return t;
		}

		if (isComplex) {
			redisTemplate.opsForValue().set(cacheKey, gson.toJson(t));
		} else {
			redisTemplate.opsForValue().set(cacheKey, JsonUtil.toJson(t));
		}
		redisTemplate.expire(cacheKey, timeout, TimeUnit.SECONDS);

		return t;
	}

	private String getValue(final String cacheKey) {
		// final boolean isExsit = redisTemplate.execute(new
		// RedisCallback<Boolean>() {
		// @Override
		// public Boolean doInRedis(final RedisConnection connection) {
		// return connection.get(cacheKey.getBytes()) != null;
		// }
		// });

		return redisTemplate.opsForValue().get(cacheKey);
	}

}
