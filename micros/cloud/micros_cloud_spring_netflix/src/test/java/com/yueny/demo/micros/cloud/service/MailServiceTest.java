package com.yueny.demo.micros.cloud.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yueny.demo.micros.TestContextConfiguration;

/**
 * 邮件服务
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年5月19日 下午10:05:24
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestContextConfiguration.class)
public class MailServiceTest {
	@Autowired
	private IMailService mailService;

	@Test
	public void testSimpleMail() throws Exception {
		mailService.sendMail(Arrays.asList("yueny09@163.com"), "test simple mail", " hello this is simple mail");
	}

	@Test
	public void testTemplateMail() throws Exception {
		final Map<String, String> maps = new HashMap<>();
		maps.put("id", "666");

		mailService.sendTemplateMail(Arrays.asList("yueny09@163.com"), "主题：这是模板邮件", "emailTemplate", maps);
	}

}
