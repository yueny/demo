package com.yueny.demo.rocketmq.spring.context.config.adapter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * HttpMessageConverter
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年4月28日 下午11:39:23
 *
 */
@Configuration
public class ExtendInterceptorConfigurerAdapter extends WebMvcConfigurerAdapter {
	@Override
	public void addInterceptors(final InterceptorRegistry registry) {
		// 多个拦截器组成一个拦截器链
		// addPathPatterns 用于添加拦截规则
		// excludePathPatterns 用户排除拦截

		// final ServiceContextInterceptor interceptor = new
		// ServiceContextInterceptor();
		// // interceptor.setSystemCode(systemCode);
		// registry.addInterceptor(interceptor).addPathPatterns("/**").excludePathPatterns("/content/**");
	}

	@Override
	public void addResourceHandlers(final ResourceHandlerRegistry registry) {
		/*
		 * 静态内容
		 *
		 * 默认情况下，Spring
		 * Boot从classpath下一个叫/static（/public，/resources或/META-INF/resources）
		 * 的文件夹或从ServletContext根目录提供静态内容。这使用了Spring
		 * MVC的ResourceHttpRequestHandler，
		 * 所以你可以通过添加自己的WebMvcConfigurerAdapter并覆写addResourceHandlers方法来改变这个行为（
		 * 加载静态文件）。
		 *
		 * 注：如果你的应用将被打包成jar，那就不要使用src/main/webapp文件夹。尽管该文件夹是一个共同的标准，
		 * 但它仅在打包成war的情况下起作用，并且如果产生一个jar，多数构建工具都会静悄悄的忽略它。
		 */
		registry.addResourceHandler("/**").addResourceLocations("/WEB-INF/resources/").setCachePeriod(0);
	}

	// /*
	// * (non-Javadoc)
	// *
	// * @see org.springframework.web.servlet.config.annotation.
	// * WebMvcConfigurationSupport#configureMessageConverters(java.util.List)
	// */
	// @Override
	// public void configureMessageConverters(final
	// List<HttpMessageConverter<?>> converters) {
	// converters.add(new ByteArrayHttpMessageConverter());
	//
	// final StringHttpMessageConverter stringHttpMessageConverter = new
	// StringHttpMessageConverter(
	// Charset.forName("UTF-8"));
	// stringHttpMessageConverter.setWriteAcceptCharset(false);
	// converters.add(stringHttpMessageConverter);
	//
	// converters.add(new ResourceHttpMessageConverter());
	//
	// converters.add(fastJsonHttpMessageConverter());
	// converters.add(new
	// CustomAllEncompassingFormHttpMessageConverter("UTF-8"));
	// // com.yueny.rapid.lang.json.CustomMappingJackson2HttpMessageConverter
	// }
	//
	// /**
	// * json转换器.避免IE执行AJAX时,返回JSON出现下载文件
	// */
	// @Bean
	// public FastJsonHttpMessageConverter fastJsonHttpMessageConverter() {
	// final FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new
	// FastJsonHttpMessageConverter();
	// fastJsonHttpMessageConverter
	// .setSupportedMediaTypes(Arrays.asList(new MediaType("application",
	// "json", Charset.forName("UTF-8")),
	// new MediaType("application", "*+json", Charset.forName("UTF-8")),
	// new MediaType("text", "html", Charset.forName("UTF-8"))));
	//
	// return fastJsonHttpMessageConverter;
	// }
}
