package com.yueny.demo.micros.cloud.spring.context.config.wrapper;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

/**
 * 邮件配置
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年5月11日 上午11:11:04
 *
 */
@Configuration
public class MailConfig {
	@Autowired
	private Environment env;

	@Bean(name = "javaMailSender")
	public JavaMailSender getSender() {
		final JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		javaMailSender.setUsername(env.getProperty("spring.mail.username"));
		javaMailSender.setHost(env.getProperty("spring.mail.host"));
		// javaMailSender.setPort(587);// ①
		javaMailSender.setDefaultEncoding(env.getProperty("spring.mail.default-encoding"));
		final Properties props = new Properties();// ②
		props.setProperty("mail.smtp.host", env.getProperty("spring.mail.password"));
		props.setProperty("mail.smtp.auth", "true");
		final javax.mail.Session session = javax.mail.Session.getDefaultInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(javaMailSender.getUsername(),
						env.getProperty("spring.mail.password"));
			}
		});
		javaMailSender.setSession(session);// ③
		return javaMailSender;
	}

}
