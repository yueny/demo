package com.yueny.demo.micros.boot.spring.configure.core.context.config.db;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;

/**
 * Druid监控服务器
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年9月7日 下午7:01:45
 *
 */
@Configuration
public class DruidConfiguration {
	/**
	 * @return 过滤器
	 */
	@Bean
	public FilterRegistrationBean statFilter() {
		final FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());

		// 添加过滤规则
		filterRegistrationBean.addUrlPatterns("/*");
		// 忽略过滤的格式
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");

		return filterRegistrationBean;
	}

	/**
	 * @return 监控服务器，运行后访问 http://xxxx/druid/datasource.html
	 */
	@Bean
	public ServletRegistrationBean statViewServle() {
		final ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),
				"/druid/*");

		// ip白名单
		servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
		// ip黑名单(共同存在时，deny优先于allow)
		servletRegistrationBean.addInitParameter("deny", "192.168.0.1");

		// 控制管理台用户
		servletRegistrationBean.addInitParameter("loginUsername", "admin");
		servletRegistrationBean.addInitParameter("loginPassword", "admin");

		// 是否能够重置数据
		servletRegistrationBean.addInitParameter("resetEnable", "false");

		return servletRegistrationBean;
	}

}
