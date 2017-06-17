package com.yueny.demo.job.config;

import java.io.StringReader;
import java.util.Properties;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.taobao.diamond.client.DiamondClients;
import com.taobao.diamond.manager.DiamondManager;
import com.taobao.diamond.manager.ManagerListener;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SettingsConfigure implements InitializingBean {
	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("DiamondManager load init ... ");

		final DiamondManager diamondManager = DiamondClients.createSafeDiamondManager("11", "DEFAULT_GROUP",
				new ManagerListener() {
					@Override
					public Executor getExecutor() {
						return null;
					}

					@Override
					public void receiveConfigInfo(final String configInfo) {
						log.info("DiamondManager receiveConfigInfo :{}.", configInfo);

						// 配置变更异步通知
						try {
							final Properties properties = new Properties();
							properties.load(new StringReader(configInfo));

							System.out.println("更新了：" + configInfo);
						} catch (final Throwable ignore) {
							// .
						}
					}
				});

	}

}
