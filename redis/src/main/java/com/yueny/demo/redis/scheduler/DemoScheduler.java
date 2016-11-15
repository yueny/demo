package com.yueny.demo.redis.scheduler;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.yueny.rapid.lang.date.DateTimeUtil;
import com.yueny.rapid.lang.util.UuidUtil;

/**
 *
 * @author <a href="mailto:yueny09@126.com"> 袁洋 2014年12月9日 下午4:52:34
 *
 * @category tag
 */
@Service
public class DemoScheduler extends SuperScheduler implements InitializingBean {
	private static final String sortSetKey = "DEMO.XSET.LOCAL";
	private final Map<String, Double> mms = new HashMap<>();
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Override
	public void afterPropertiesSet() throws Exception {
		if (redisTemplate.opsForZSet().size(sortSetKey) > 0) {
			redisTemplate.opsForZSet().removeRangeByScore(sortSetKey, getPreviosMintue(2), getPreviosMintue(1));
		}

		// eg: 66.88 min
		final Double currentHourTimes = getCurrentMintue();
		logger.info("currentHourTimes is :{}", currentHourTimes);
		logger.info("currentHourTimes step is {}/{}/{}", currentHourTimes + 1.1, currentHourTimes + 2.2,
				currentHourTimes + 3.3);

		for (int i = 0; i < 4; i++) {
			mms.put(UuidUtil.getSimpleUuid(), currentHourTimes + (i * 1.1d));
		}

		for (final Map.Entry<String, Double> entry : mms.entrySet()) {
			redisTemplate.opsForZSet().add(sortSetKey, entry.getKey(), entry.getValue());
		}
	}

	/**
	 * 每5秒钟删除一次当前时间10秒之前的数据
	 */
	@Scheduled(cron = "0/5 * * * * ?")
	public void autoLogs() {
		for (final Map.Entry<String, Double> entry : mms.entrySet()) {
			final double s = redisTemplate.opsForZSet().score(sortSetKey, entry.getKey());
			logger.info("value is {}, score is {}.", entry.getKey(), s);
		}

		if (redisTemplate.opsForZSet().size(sortSetKey) > 0) {
			redisTemplate.opsForZSet().removeRangeByScore(sortSetKey, getPreviosMintue(2), getPreviosMintue(1));
		}

		// 全局token的有效期为10-20分钟波动, 获取过去10分钟至20分钟产生的token. eg: 66.88 min
		final double currentHourTimes = getCurrentMintue();
		logger.info("currentHourTimes is :{}, between {} and {}.", currentHourTimes, currentHourTimes - 2.0,
				currentHourTimes - 1.0);
		final Set<String> values = redisTemplate.opsForZSet().rangeByScore(sortSetKey, currentHourTimes - 2.0,
				currentHourTimes - 1.0);

		if (CollectionUtils.isEmpty(values)) {
			System.out.println("无可执行sort set!");
			return;
		}

		for (final String v : values) {
			System.out.println(v + " score is " + redisTemplate.opsForZSet().score(sortSetKey, v));
		}
	}

	/**
	 * @return 得到当前分钟数
	 */
	private Double getCurrentMintue() {
		// 24h * 60min * 60sec
		return getCurrentSeconds() / 60 / 1.0;
	}

	/**
	 * @return 得到当前秒数
	 */
	private Long getCurrentSeconds() {
		final Long currentSeconds = DateTimeUtil.nowTime(false) / 1000;

		// eg: 66.88 min
		System.out.println(currentSeconds);
		System.out.println(currentSeconds / 60);
		System.out.println(currentSeconds / 60 / 1.0);

		return currentSeconds;
	}

	/**
	 * @return 得到当前时间之前的分钟数
	 */
	private Double getPreviosMintue(final int previos) {
		final Long previosSeconds = getCurrentSeconds() - 60 * previos;
		return previosSeconds / 60 / 1.0;
	}
}