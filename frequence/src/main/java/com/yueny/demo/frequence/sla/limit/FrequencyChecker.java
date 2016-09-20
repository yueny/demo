package com.yueny.demo.frequence.sla.limit;

import javax.servlet.http.HttpServletRequest;

import com.yueny.demo.frequence.sla.FrequencyException;
import com.yueny.demo.frequence.sla.limit.strategy.IFrequencyStrategy;
import com.yueny.demo.frequence.sla.limit.strategy.MemoryFrequencyStrategy;

import lombok.Setter;

/**
 * 访问频率限制检查
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月15日 下午9:14:57
 *
 */
@Deprecated
public class FrequencyChecker {
	@Setter
	private IFrequencyStrategy frequencyStrategy = new MemoryFrequencyStrategy();

	public void check(final HttpServletRequest request, final int seconds, final Object handler)
			throws FrequencyException {
		final String uri = request.getRequestURI();// 包含ContextPath也没有问题

		// IP+url
		final String key = "ul:" + request.getRemoteAddr() + ":" + uri;

		// final boolean success = frequencyStrategy.set(key);
		// if (!success) {
		// // 服务拒绝
		// throw new FrequencyException(request.getRemoteAddr(), uri);
		// }
	}
}
