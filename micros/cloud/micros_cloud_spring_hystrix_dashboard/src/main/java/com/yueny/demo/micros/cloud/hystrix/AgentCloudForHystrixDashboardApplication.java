package com.yueny.demo.micros.cloud.hystrix;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

import lombok.extern.slf4j.Slf4j;

@SpringCloudApplication
// 启用Hystrix Dashboard功能
@EnableHystrixDashboard
@Slf4j
public class AgentCloudForHystrixDashboardApplication implements DisposableBean {
	public static void main(final String[] args) {
		try {
			SpringApplication.run(AgentCloudForHystrixDashboardApplication.class, args);
		} catch (final Exception e) {
			log.error("服务启动异常:", e);
		}
	}

	/*
	 * Application exit
	 *
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	@Override
	public void destroy() throws Exception {
		log.error("服务关闭了~~~");
	}

}
