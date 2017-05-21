package com.yueny.demo.micros.cloud.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.google.common.util.concurrent.MoreExecutors;
import com.yueny.demo.micros.cloud.scheduler.MailSendEntry;
import com.yueny.demo.micros.cloud.scheduler.MailSendQueue;
import com.yueny.demo.micros.cloud.scheduler.runner.MailSendRunner;
import com.yueny.demo.micros.cloud.service.IMailService;
import com.yueny.superclub.util.exec.async.NamedThreadFactory;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.schedulers.Schedulers;

/**
 * 邮件服务
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年5月19日 下午10:01:04
 *
 */
@Service
public class MailServiceImpl implements IMailService {
	private static ExecutorService emailExecutor = MoreExecutors
			.listeningDecorator(MoreExecutors.getExitingExecutorService(new ThreadPoolExecutor(2, 2, 0L,
					TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new NamedThreadFactory())));

	@Value("${spring.mail.username}")
	private String from;

	@Autowired
	private JavaMailSender javaMailSender;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private TemplateEngine templateEngine;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.yueny.demo.micros.cloud.service.IMailService#sendMail(java.util.List,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public boolean sendMail(final List<String> to, final String subject, final String content) {
		if (CollectionUtils.isEmpty(to)) {
			logger.error("收件人不能为空！发件主题:{}, 发件内容:{}", to, subject, StringUtils.substring(content, 0, 88));
			return false;
		}

		final MailSendQueue mailSendQueue = MailSendQueue.builder().from(from).to(to).subject(subject).content(content)
				.build();
		final Future<Boolean> future = emailExecutor.submit(new MailSendRunner(javaMailSender, mailSendQueue));

		final MailSendEntry<Boolean> entry = new MailSendEntry<Boolean>();
		entry.setQueue(mailSendQueue);
		entry.setFuture(future);

		toStorage(entry);

		return true;
	}

	@Override
	public boolean sendMail(final String to, final String subject, final String content) {
		return sendMail(Arrays.asList(to), subject, content);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.yueny.demo.micros.cloud.service.IMailService#sendTemplateMail(java.
	 * util.List, java.lang.String, java.lang.String, java.util.Map)
	 */
	@Override
	public boolean sendTemplateMail(final List<String> to, final String subject, final String emailTemplate,
			final Map<String, String> maps) {
		if (CollectionUtils.isEmpty(to)) {
			logger.error("收件人不能为空！发件主题:{}", to, subject);
			return false;
		}

		final String content = getTemplateEngine(emailTemplate, maps);
		return sendMail(to, subject, content);
	}

	@Override
	public boolean sendTemplateMail(final String to, final String subject, final String emailTemplate,
			final Map<String, String> maps) {
		return sendTemplateMail(Arrays.asList(to), subject, emailTemplate, maps);
	}

	private String getTemplateEngine(final String emailTemplate, final Map<String, String> maps) {
		// 创建邮件正文
		final Context context = new Context();
		for (final Map.Entry<String, String> entry : maps.entrySet()) {
			context.setVariable(entry.getKey(), entry.getValue());
		}
		final String emailContent = templateEngine.process(emailTemplate, context);

		return emailContent;
	}

	/**
	 * 发送结果入库
	 */
	private void toStorage(final MailSendEntry<Boolean> entry) {
		// 异步线程读数据库
		final Observable<MailSendEntry<Boolean>> observable = Observable
				.create(new ObservableOnSubscribe<MailSendEntry<Boolean>>() {
					@Override
					public void subscribe(final ObservableEmitter<MailSendEntry<Boolean>> emitter) throws Exception {
						logger.info("subscribe thread is :{} ~~~", Thread.currentThread().getName());

						emitter.onNext(entry);
					}
				});

		final Consumer<MailSendEntry<Boolean>> consumer = new Consumer<MailSendEntry<Boolean>>() {
			@Override
			public void accept(final MailSendEntry<Boolean> et) throws Exception {
				if (et.getFuture().get()) {
					logger.info("邮件发送成功, 总耗时:{} 秒, thread is :{} ~~~", et.durnSecond(),
							Thread.currentThread().getName());
					System.out.println("insert " + et);
				} else {
					logger.warn("邮件发送失败, thread is :{},", Thread.currentThread().getName());
				}
			}
		};

		observable
				// 指定的是上游发送事件的线程. 多次指定上游的线程只有第一次指定的有效,
				// 也就是说多次调用subscribeOn() 只有第一次的有效, 其余的会被忽略.
				.subscribeOn(Schedulers.computation())
				// 指定的是下游接收事件的线程. 多次指定下游的线程是可以的,
				// 也就是说每调用一次observeOn() , 下游的线程就会切换一次.
				.observeOn(new ExecutorScheduler(Executors.newFixedThreadPool(3))).subscribe(consumer);

	}

}
