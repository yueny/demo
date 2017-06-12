/**
 *
 */
package com.yueny.demo.spring.context.config.adapter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.yueny.rapid.data.log.trace.web.filter.mdc.WebLogMdcHandlerInterceptor;

/**
 * 拦截器扩展
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年5月10日 下午4:47:31
 * @since
 */
@Configuration
public class ExtendInterceptorConfigurerAdapter extends WebMvcConfigurerAdapter {
	@Override
	public void addInterceptors(final InterceptorRegistry registry) {
		registry.addInterceptor(new WebLogMdcHandlerInterceptor()).addPathPatterns("/**")
				.excludePathPatterns("/demo/**");
	}

}
