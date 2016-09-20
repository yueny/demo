package com.yueny.demo.frequence.sla.config;

import java.util.Observable;

import lombok.Getter;
import lombok.Setter;

/**
 * 限流配置项，形式如下： uri => {"enable":"true","threshold":100}
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年7月16日 下午1:48:58
 *
 */
public class RateLimitConfigEntry extends Observable {
	/**
	 * 默认阈值
	 */
	public static final int DEFAULT_THRESHOLD = 200;
	@Getter
	private boolean enable = true;
	@Setter
	@Getter
	private String keyName;
	@Getter
	private int threshold = DEFAULT_THRESHOLD;

	/**
	 * 默认空构造方法
	 */
	public RateLimitConfigEntry() {
	}

	/**
	 * 构造方法，设置键名，创建配置项
	 *
	 * @param keyName
	 *            配置项的键名
	 */
	public RateLimitConfigEntry(final String keyName) {
		this.keyName = keyName;
	}

	public void setEnable(final boolean enable) {
		this.enable = enable;
		setChanged();
	}

	public void setThreshold(final int threshold) {
		this.threshold = threshold;
		setChanged();
	}
}
