package com.yueny.demo.frequence.sla.limit;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.method.HandlerMethod;

import com.yueny.demo.frequence.sla.config.annotation.Frequency;

/**
 * FrequencyResolver
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月15日 下午2:54:15
 *
 */
@Deprecated
public class FrequencyResolver {
	private final Map<Integer, Integer> data = new ConcurrentHashMap<Integer, Integer>();

	public Integer getSeconds(final Object handler) {
		final int hashCode = handler.hashCode();
		Integer seconds = data.get(hashCode);
		if (seconds != null) {
			return seconds;
		}
		seconds = this.parseSeconds(handler);
		data.put(hashCode, seconds);
		return seconds;
	}

	private int parseSeconds(final Object handler) {
		if (!(handler instanceof HandlerMethod)) {
			return 0;
		}

		final HandlerMethod handlerMethod = (HandlerMethod) handler;
		final Frequency frequency = handlerMethod.getMethodAnnotation(Frequency.class);
		if (frequency == null) {
			return 0;
		}
		// return frequency.millseconds();
		return 0;
	}

}
