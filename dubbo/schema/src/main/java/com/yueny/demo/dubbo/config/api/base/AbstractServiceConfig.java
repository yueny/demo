package com.yueny.demo.dubbo.config.api.base;

import lombok.Getter;
import lombok.Setter;

/**
 * 抽象服务配置
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年4月1日 下午5:21:53
 *
 */
public abstract class AbstractServiceConfig extends AbstractInterfaceConfig {
	/**
	 *
	 */
	private static final long serialVersionUID = 2801285178457371783L;

	/**
	 * 允许执行请求数
	 */
	@Getter
	@Setter
	private Integer executes;
	/**
	 * 服务分组
	 */
	@Getter
	@Setter
	protected String group;
}
