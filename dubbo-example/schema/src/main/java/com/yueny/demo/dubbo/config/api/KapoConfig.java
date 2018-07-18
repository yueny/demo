package com.yueny.demo.dubbo.config.api;

import com.yueny.demo.dubbo.config.api.base.AbstractServiceConfig;

import lombok.Getter;
import lombok.Setter;

/**
 * 持久层配置项
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年4月1日 下午5:57:01
 *
 * @param <T>
 */
public class KapoConfig extends AbstractServiceConfig {
	/**
	 *
	 */
	private static final long serialVersionUID = -7622525199686906667L;

	// // 接口实现类引用
	// private T ref;
	/**
	 * 接口类型
	 */
	private String interfaceName;

	public String getInterface() {
		return interfaceName;
	}

	public void setInterface(String interfaceName) {
		this.interfaceName = interfaceName;
		if (id == null || id.length() == 0) {
			id = interfaceName;
		}
	}

}
