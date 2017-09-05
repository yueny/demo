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
 * redis集成注解缓存
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年9月4日 下午4:20:53
 *
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {
	@Bean
	public CacheManager cacheManager(@SuppressWarnings("rawtypes") final RedisTemplate redisTemplate) {
		return new RedisCacheManager(redisTemplate);
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
		template.afterPropertiesSet();
		return template;
	}

	@Bean
	public KeyGenerator wiselyKeyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(final Object target, final Method method, final Object... params) {
				final StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append(method.getName());
				for (final Object obj : params) {
					sb.append(obj.toString());
				}
				return sb.toString();
			}
		};
	}
}
