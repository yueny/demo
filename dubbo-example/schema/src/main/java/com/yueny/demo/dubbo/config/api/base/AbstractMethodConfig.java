package com.yueny.demo.dubbo.config.api.base;

import lombok.Getter;
import lombok.Setter;

/**
 * 抽象属性配置
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年4月1日 下午5:14:57
 *
 */
public abstract class AbstractMethodConfig extends AbstractConfig {
	/**
	 *
	 */
	private static final long serialVersionUID = -4882570759341869610L;

	/**
	 * 最大并发调用
	 */
	@Getter
	@Setter
	protected Integer actives;
}
