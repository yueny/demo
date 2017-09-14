package com.yueny.demo.micros.cloud.scheduler;

import java.util.List;

import com.yueny.rapid.lang.mask.annotation.Mask;
import com.yueny.rapid.lang.mask.pojo.instance.AbstractMaskBo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 邮件发送对象
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年5月21日 下午6:06:16
 *
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailSendQueue extends AbstractMaskBo {
	/**
	 * 发件内容
	 */
	@Mask
	private String content;
	/**
	 * 发件人
	 */
	private String from;
	/**
	 * 发件主题
	 */
	private String subject;
	/**
	 * 收件人
	 */
	private List<String> to;

}
