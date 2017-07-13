package com.yueny.demo.micros.cloud.observable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import com.yueny.rapid.lang.util.io.ResourcesLoader;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

public class FlowableForReadFileTest {
	/**
	 * 得到文件
	 *
	 * @param absoluteFilePath
	 *            文件路径
	 */
	private static File getFile(final String absoluteFilePath) {
		File file = null;
		try {
			file = ResourcesLoader.getResourceAsFile(absoluteFilePath);
		} catch (final IOException e) {
			// ignore
		}

		if (file == null) {
			file = new File(absoluteFilePath);
		}

		return file;
	}

	private final String FILE_NAME = "log/service.log";

	@Test
	public void testRead() {
		System.out.println("输出 " + FILE_NAME + "日志内容");
		System.out.println("#####################");

		Flowable.create(new FlowableOnSubscribe<String>() {
			@Override
			public void subscribe(final FlowableEmitter<String> emitter) throws Exception {
				try {
					final FileReader reader = new FileReader(getFile(FILE_NAME));
					final BufferedReader br = new BufferedReader(reader);

					String str;

					while ((str = br.readLine()) != null && !emitter.isCancelled()) {
						while (emitter.requested() == 0) {
							if (emitter.isCancelled()) {
								break;
							}
						}
						emitter.onNext(str);
					}

					br.close();
					reader.close();

					emitter.onComplete();
				} catch (final Exception e) {
					emitter.onError(e);
				}
			}
		}, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io()).observeOn(Schedulers.single())
				.subscribe(new Subscriber<String>() {
					Subscription subs;

					@Override
					public void onComplete() {
					}

					@Override
					public void onError(final Throwable t) {
						System.out.println(t);
					}

					@Override
					public void onNext(final String string) {
						System.out.println(string);
						try {
							Thread.sleep(100);

							subs.request(5);
						} catch (final InterruptedException e) {
							e.printStackTrace();
						}
					}

					@Override
					public void onSubscribe(final Subscription s) {
						subs = s;
						s.request(1);
					}
				});

		try {
			TimeUnit.MILLISECONDS.sleep(60000);
		} catch (final InterruptedException e) {
			System.out.println("sleep Interrupted!");
		}
	}

}
