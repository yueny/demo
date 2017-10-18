/**
 *
 */
package com.yueny.demo.cas.client.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.yueny.rapid.lang.json.JsonUtil;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年5月4日 下午2:56:17
 * @since 1.0.0
 */
public abstract class BaseController {
	/**
	 * 存放当前线程的Model对象
	 */
	private final static ThreadLocal<Model> httpModelThreadLocal = new ThreadLocal<>();
	/**
	 * 存放当前线程的HttpServletRequest对象
	 */
	private final static ThreadLocal<HttpServletRequest> httpServletRequestThreadLocal = new ThreadLocal<>();
	/**
	 * 存放当前线程的ServletResponse对象
	 */
	private final static ThreadLocal<ServletResponse> httpServletResponseThreadLocal = new ThreadLocal<>();

	/**
	 * Logger
	 */
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * Add the supplied attribute under the supplied name.
	 *
	 * @param attributeName
	 *            the name of the model attribute (never {@code null})
	 * @param attributeValue
	 *            the model attribute value (can be {@code null})
	 */
	protected Model addAttribute(final String attributeName, final Object attributeValue) {
		return getModel().addAttribute(attributeName, attributeValue);
	}

	/**
	 * 获取当前线程的Model对象
	 *
	 * @return 当前线程的Model对象
	 */
	protected Model getModel() {
		return httpModelThreadLocal.get();
	}

	/**
	 * 获取当前线程的HttpServletRequest对象
	 *
	 * @return 当前线程的HttpServletRequest对象
	 */
	protected HttpServletRequest getRequest() {
		return httpServletRequestThreadLocal.get();
	}

	/**
	 * 参数转换,key为空spring会自动过滤掉
	 *
	 * @param request
	 *            HttpServletRequest
	 * @return 转换后的参数
	 */
	protected Map<String, String> getRequestParameter(final HttpServletRequest request) {
		final Map<String, String> requestParam = new HashMap<String, String>();
		for (final Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
			// String key = getValueTrimToEmpty(entry.getKey());
			final String key = entry.getKey();
			if (entry.getValue() == null || StringUtils.isBlank(entry.getValue()[0])) {
				requestParam.put(key, StringUtils.EMPTY);
			}
			requestParam.put(key, entry.getValue()[0]);
			// requestParam.put(key, getValueTrimToEmpty(entry.getValue()[0]));
		}
		return requestParam;
	}

	/**
	 * 获取当前线程的ServletResponse对象
	 *
	 * @return 当前线程的ServletResponse对象
	 */
	protected ServletResponse getResponse() {
		return httpServletResponseThreadLocal.get();
	}

	/**
	 * resp数据回写
	 */
	protected boolean respWrite(final String repsData) {
		getResponse().setContentType("type=text/html;charset=UTF-8");
		try {
			getResponse().getWriter().write(JsonUtil.toJson(repsData));
			getResponse().flushBuffer();

			return true;
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 使用@ModelAttribute注解标识的方法会在每个控制器中的方法访问之前先调用
	 */
	@ModelAttribute
	protected void setThreadLocal(final HttpServletRequest request, final ServletResponse response, final Model model) {
		httpServletRequestThreadLocal.set(request);
		httpServletResponseThreadLocal.set(response);
		httpModelThreadLocal.set(model);
	}

}
