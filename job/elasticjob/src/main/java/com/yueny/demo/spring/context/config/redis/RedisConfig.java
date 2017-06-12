package com.yueny.demo.spring.context.config.redis;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import redis.clients.jedis.JedisPoolConfig;

//@Configuration
public class RedisConfig {
	@Value("${redis.sentinel.master.name}")
	private String masterName;
	@Value("${redis.maxIdle}")
	private int maxIdle;
	@Value("${redis.maxTotal}")
	private int maxTotal;
	@Value("${redis.maxWaitMillis}")
	private long maxWaitMillis;
	@Value("${redis.client.password}")
	private String password;
	@Value("${redis.sentinel.1.host}")
	private String sentinel_1_host;
	@Value("${redis.sentinel.1.port}")
	private String sentinel_1_port;
	@Value("${redis.sentinel.2.host}")
	private String sentinel_2_host;
	@Value("${redis.sentinel.2.port}")
	private String sentinel_3_port;
	@Value("${redis.testOnBorrow}")
	private boolean testOnBorrow;

	// /**
	// * 分布式锁实例
	// */
	// @Bean(name = "lock")
	// public DistributedRedisLock lock(@Qualifier("stringRedisTemplate") final
	// StringRedisTemplate stringRedisTemplate) {
	// final DistributedRedisLock lock = new DistributedRedisLock();
	// lock.setRedisTemplate(stringRedisTemplate);
	// return lock;
	// }

	@Bean(name = "poolConfig")
	public JedisPoolConfig poolConfig() {
		final JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(maxIdle);
		poolConfig.setMaxTotal(maxTotal);
		poolConfig.setMaxWaitMillis(maxWaitMillis);
		poolConfig.setTestOnBorrow(testOnBorrow);
		return poolConfig;
	}

	@Bean(name = "redisConnectionFactory")
	public JedisConnectionFactory redisConnectionFactory(
			@Qualifier("redisSentinelConfig") final RedisSentinelConfiguration redisSentinelConfig,
			@Qualifier("poolConfig") final JedisPoolConfig poolConfig) {
		final JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory(redisSentinelConfig,
				poolConfig);
		redisConnectionFactory.setPoolConfig(poolConfig);
		redisConnectionFactory.setPassword(password);
		return redisConnectionFactory;
	}

	@Bean(name = "redisSentinelConfig")
	public RedisSentinelConfiguration redisSentinelConfig() {
		final String sentinel_1 = sentinel_1_host + ":" + sentinel_1_port;
		final String sentinel_2 = sentinel_2_host + ":" + sentinel_3_port;
		final Set<String> sentinelSet = new HashSet<>();
		sentinelSet.add(sentinel_1);
		sentinelSet.add(sentinel_2);
		final RedisSentinelConfiguration redisSentinelConfig = new RedisSentinelConfiguration(masterName, sentinelSet);
		return redisSentinelConfig;
	}

	@Bean(name = "redisTemplate")
	public RedisTemplate<?, ?> redisTemplate(
			@Qualifier("redisConnectionFactory") final JedisConnectionFactory redisConnectionFactory) {
		final RedisTemplate<?, ?> redisTemplate = new RedisTemplate();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		return redisTemplate;
	}

	@Bean(name = "stringRedisTemplate")
	public StringRedisTemplate stringRedisTemplate(
			@Qualifier("redisConnectionFactory") final JedisConnectionFactory redisConnectionFactory) {
		final StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
		stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
		return stringRedisTemplate;
	}

}
