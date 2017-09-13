package com.yueny.demo.micros.cloud.service;

import java.util.List;
import java.util.Map;

/**
 * 邮件服务
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年5月19日 下午9:58:52
 *
 */
public interface IMailService {
	/**
	 * 邮件发送<br>
	 * 简单文本、 HTML格式
	 *
	 * @param to
	 *            收件人
	 * @param subject
	 *            主题
	 * @param content
	 *            邮件内容
	 */
	boolean sendMail(List<String> to, String subject, String content);

	/**
	 * 邮件发送
	 *
	 * @param to
	 *            收件人
	 * @param subject
	 *            主题
	 * @param content
	 *            邮件内容
	 */
	boolean sendMail(String to, String subject, String content);

	/**
	 * 模板邮件发送
	 *
	 * @param to
	 *            收件人
	 * @param subject
	 *            主题
	 * @param emailTemplate
	 *            模板，如'email/emailTemplate.html'，则输入"email/emailTemplate"
	 * @param maps
	 *            邮件内容参数
	 */
	boolean sendTemplateMail(final List<String> to, final String subject, final String emailTemplate,
			final Map<String, String> maps);

	/**
	 * 模板邮件发送
	 *
	 * @param to
	 *            收件人
	 * @param subject
	 *            主题
	 * @param emailTemplate
	 *            模板
	 * @param maps
	 *            邮件内容参数
	 */
	boolean sendTemplateMail(String to, final String subject, final String emailTemplate,
			final Map<String, String> maps);

}
