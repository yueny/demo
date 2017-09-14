package com.yueny.demo.micros.cloud.scheduler.runner;

import java.util.concurrent.Callable;

import javax.mail.internet.MimeMessage;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.google.common.base.Joiner;
import com.yueny.demo.micros.cloud.scheduler.MailSendQueue;
import com.yueny.rapid.lang.util.StringUtil;

/**
 * 邮件发送线程
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年5月21日 下午6:04:28
 *
 */
public class MailSendRunner implements Callable<Boolean> {
	/**
	 * JavaMailSender
	 */
	private final JavaMailSender javaMailSender;
	private final Logger logger = LoggerFactory.getLogger(MailSendRunner.class);
	/**
	 * 邮件发送对象
	 */
	private final MailSendQueue queue;

	public MailSendRunner(final JavaMailSender javaMailSender, final MailSendQueue queue) {
		this.javaMailSender = javaMailSender;
		this.queue = queue;
	}

	@Override
	public Boolean call() throws Exception {
		if (queue == null || CollectionUtils.isEmpty(queue.getTo())) {
			return false;
		}

		final MimeMessagePreparator preparator = new MimeMessagePreparator() {
			@Override
			public void prepare(final MimeMessage mimeMessage) throws Exception {
				final MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				message.setFrom(queue.getFrom());

				String[] tos = new String[queue.getTo().size()];
				tos = StringUtil.makeStringToArray(Joiner.on("|").join(queue.getTo()), "|");
				// for (int i = 0; i < to.size(); i++) {
				// tos[i] = to.get(i);
				// }
				message.setTo(tos);

				message.setSubject(queue.getSubject());
				message.setText(queue.getContent(), true);
			}
		};

		try {
			javaMailSender.send(preparator);
			logger.info("邮件已经发送完成~~~");
			return true;
		} catch (final Exception e) {
			logger.error("发送邮件时发生异常！", e);
		}

		return false;
	}

}
