package com.yueny.demo.micros.boot.spring.configure.core.context.config.redis;

import java.lang.reflect.Method;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * redis集成注解缓存管理（注解用）
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年9月4日 下午4:20:53
 *
 */
@Configuration
@EnableCaching // 启用缓存
public class RedisConfig extends CachingConfigurerSupport {
	@Bean
	public CacheManager cacheManager(@SuppressWarnings("rawtypes") final RedisTemplate redisTemplate) {
		final RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
		// 设置缓存过期时间, 秒
		rcm.setDefaultExpiration(15);

		// // 设置value的过期时间
		// final Map<String, Long> map = new HashMap<String, Long>();
		// map.put("test", 60L);
		// rcm.setExpires(map);

		return rcm;
	}

	/**
	 * RedisTemplate初始化
	 *
	 * @param factory
	 * @return
	 */
	@Bean
	public RedisTemplate<String, String> redisTemplate(final RedisConnectionFactory factory) {
		final StringRedisTemplate template = new StringRedisTemplate(factory);

		final Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(
				Object.class);

		final ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);

		template.setValueSerializer(jackson2JsonRedisSerializer);
		template.setHashValueSerializer(jackson2JsonRedisSerializer);
		template.afterPropertiesSet();
		return template;
	}

	/**
	 * 此方法将会根据类名+方法名+所有参数的值生成唯一的一个key,即使@Cacheable中的value属性一样，key也会不一样。
	 */
	@Bean
	public KeyGenerator wiselyKeyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(final Object target, final Method method, final Object... params) {
				// This will generate a unique key of the class name, the method
				// name and all method parameters appended.
				final StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append(method.getName());

				sb.append("#");
				// final Cacheable cacheable =
				// method.getAnnotation(Cacheable.class);
				// if (cacheable != null) {
				// for (final String s : cacheable.value()) {
				// sb.append(s);
				// }
				// sb.append("-");
				// }

				for (final Object obj : params) {
					sb.append(obj.toString());
				}
				return sb.toString();
			}
		};
	}
}
