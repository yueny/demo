package com.yueny.demo.metrics.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.yueny.demo.metrics.util.MetricRegistryHelper;

/**
 * 统计
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年10月21日 下午1:37:39
 *
 */
public class MetricIntercepter extends HandlerInterceptorAdapter implements InitializingBean {
	private Meter allRequests;
	@Autowired
	private MetricRegistryHelper metricRegistryHelper;
	private Histogram responseHistogram;

	@Override
	public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response,
			final Object handler, final Exception ex) throws Exception {
		// .
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		allRequests = metricRegistryHelper.meter("ALL");
		responseHistogram = metricRegistryHelper.histogram("ALL");
	}

	@Override
	public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
			final ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
			throws Exception {
		// 计数一次
		allRequests.mark();

		responseHistogram.update(response.getBufferSize());
		return true;
	}

}
