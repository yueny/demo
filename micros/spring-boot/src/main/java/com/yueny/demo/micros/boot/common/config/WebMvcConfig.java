package com.yueny.demo.micros.boot.common.config;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.yueny.rapid.lang.json.CustomAllEncompassingFormHttpMessageConverter;

/**
 * HttpMessageConverter
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年4月28日 下午11:39:23
 *
 */
// @Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	@Override
	public void addInterceptors(final InterceptorRegistry registry) {
		// 多个拦截器组成一个拦截器链
		// addPathPatterns 用于添加拦截规则
		// excludePathPatterns 用户排除拦截

		// final ServiceContextInterceptor i = new ServiceContextInterceptor();
		// registry.addInterceptor(i);

		// <mvc:interceptor>
		// <mvc:mapping path="/**"/>
		// <mvc:exclude-mapping path="/content/**" />
		// <bean
		// class="com.yueny.superclub.service.rest.context.ServiceContextInterceptor"
		// p:systemCode="${app.system.code}"/>
		// </mvc:interceptor>
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.web.servlet.config.annotation.
	 * WebMvcConfigurationSupport#configureMessageConverters(java.util.List)
	 */
	@Override
	public void configureMessageConverters(final List<HttpMessageConverter<?>> converters) {
		converters.add(new ByteArrayHttpMessageConverter());

		final StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter(
				Charset.forName("UTF-8"));
		stringHttpMessageConverter.setWriteAcceptCharset(false);
		converters.add(stringHttpMessageConverter);

		converters.add(new ResourceHttpMessageConverter());

		converters.add(fastJsonHttpMessageConverter());
		converters.add(new CustomAllEncompassingFormHttpMessageConverter("UTF-8"));
		// com.yueny.rapid.lang.json.CustomMappingJackson2HttpMessageConverter
	}

	/**
	 * json转换器.避免IE执行AJAX时,返回JSON出现下载文件
	 */
	@Bean
	public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
		final FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
		fastJsonHttpMessageConverter
				.setSupportedMediaTypes(Arrays.asList(new MediaType("application", "json", Charset.forName("UTF-8")),
						new MediaType("application", "*+json", Charset.forName("UTF-8")),
						new MediaType("text", "html", Charset.forName("UTF-8"))));

		return fastJsonHttpMessageConverter;
	}
}
