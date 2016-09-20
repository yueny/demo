package com.yueny.demo.frequence.sla.limit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/**
 * 访问频率限制过滤器.
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月15日 下午2:53:19
 *
 */
@Component
@Deprecated
public class FrequencyInterceptor extends RegisterHandlerInterceptor {
	private final FrequencyChecker frequencyChecker = new FrequencyChecker();
	private final FrequencyResolver frequencyResolver = new FrequencyResolver();

	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
			throws Exception {
		final Integer seconds = frequencyResolver.getSeconds(handler);
		System.out.println("FrequencyInterceptor preHandle:" + handler + "seconds:" + seconds);

		if (seconds == null || seconds <= 0) {
			return true;
		}

		frequencyChecker.check(request, seconds, handler);
		return true;
	}

}
