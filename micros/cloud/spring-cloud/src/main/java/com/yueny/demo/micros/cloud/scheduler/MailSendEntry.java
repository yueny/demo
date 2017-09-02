package com.yueny.demo.micros.cloud.scheduler;

import java.util.concurrent.Future;

import com.yueny.rapid.lang.mask.pojo.instance.AbstractMaskBo;

import lombok.Getter;
import lombok.Setter;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年5月21日 下午9:58:22
 *
 * @param <T>
 */
public class MailSendEntry<T> extends AbstractMaskBo {
	/**
	 * 发送持有对象
	 */
	@Getter
	@Setter
	private Future<T> future;
	/**
	 * 邮件发送对象
	 */
	@Getter
	@Setter
	private MailSendQueue queue;

	/**
	 * 发送起始时间
	 */
	private final Long startTime;

	public MailSendEntry() {
		startTime = System.currentTimeMillis();
	}

	/**
	 * @return 耗时毫秒
	 */
	public Long durn() {
		return System.currentTimeMillis() - startTime;
	}

	/**
	 * @return 耗时秒
	 */
	public Long durnSecond() {
		return (System.currentTimeMillis() - startTime) / 1000;
	}

}