package com.yueny.demo.frequence.sla.config.parser;

import java.lang.reflect.Method;

import com.google.common.base.Joiner;
import com.yueny.demo.frequence.sla.config.annotation.Frequency;
import com.yueny.superclub.api.constant.Constants;

/**
 * 针对Web页面的 注解解析器
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月16日 下午8:11:20
 *
 */
public class WebMethodParser extends AbstractSlaRateLimitParser {
	/**
	 * web 键名前缀
	 */
	private static final String WEB_KEY_PREFIX = ISlaRateLimitParser.KEY_PREFIX + Constants.DOT + "WEB";

	/**
	 * 初始化缓存
	 */
	public WebMethodParser() {
		// .
	}

	/**
	 * 获取键名前缀，返回SLA.LIMIT.WEB
	 *
	 * @return 返回SLA.LIMIT.WEB
	 */
	@Override
	public String getKeyPrefix() {
		return WEB_KEY_PREFIX;
	}

	/**
	 * 获取解析器类，返回WebHandlerMethodParser类型
	 *
	 * @return 返回WebHandlerMethodParser类型
	 */
	@Override
	public Class<? extends ISlaRateLimitParser> getParserClass() {
		return WebMethodParser.class;
	}

	/**
	 * 解析出对应的键，规则为 前缀.(ALL|配置的键)
	 *
	 * @param method
	 *            待解析的方法
	 * @param args
	 *            方法的参数
	 *
	 * @return 键数组，内容为{前缀.ALL, 前缀.配置的键}
	 */
	@Override
	public String[] parseKeys(final Method method, final Class<?> clazz, final Object[] args) {
		final Frequency annotation = getAnnotation(method);

		// final int count = (annotation == null||
		// StringUtils.isBlank(annotation.key())) ? 1 : 2;
		final int count = 2;
		final String[] keys = new String[count];

		keys[0] = getWholeKey();
		// if (count == 2) {
		// eg.
		// 'SLA.LIMIT.WEB[com.yueny.demo.web.controller.FrequencyController#welcomeLimit(Long:String)]'
		final StringBuilder sb = new StringBuilder();
		sb.append(getKeyPrefix());
		sb.append(Constants.LEFT_BRACKET);

		final String[] parameterKeys = assemblyParameterKey(method.getParameterTypes());
		sb.append(clazz.getName());
		sb.append(Constants.CROSS_HATCH);
		sb.append(method.getName());
		sb.append(Constants.LEFT_PARENTHESES);
		sb.append(Joiner.on(Constants.COLON).join(parameterKeys));
		sb.append(Constants.RIGHT_PARENTHESES);

		sb.append(Constants.RIGHT_BRACKET);
		final String serviceKey = sb.toString();
		keys[1] = serviceKey;
		// }
		return keys;
	}
	// public String[] parseKeys(final Method method, final Object[] args) {
	// final SlaRateLimit annotation = getAnnotation(method);
	// final int count = (annotation == null || StringUtils.isBlank(annotation
	// .key())) ? 1 : 2;
	// final String[] keys = new String[count];
	// keys[0] = getKeyPrefix() + "." + ALL_KEY;
	// if (count == 2) {
	// keys[1] = getKeyPrefix() + "."
	// + StringUtils.upperCase(StringUtils.trim(annotation.key()));
	// final String subKey = getSubKey(annotation, args);
	// if (StringUtils.isNotBlank(subKey)) {
	// keys[1] = keys[1] + "." + subKey;
	// }
	// }
	// return keys;
	// }

	private String[] assemblyParameterKey(final Class<?>[] clazzs) {
		final String[] parameterKeys = new String[clazzs.length];
		for (int i = 0; i < clazzs.length; i++) {
			parameterKeys[i] = clazzs[i].getSimpleName();
		}
		return parameterKeys;
	}

}
