package com.yueny.demo.annotations.processor;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

/**
 * 注解处理器<br>
 *
 * 注册处理器：打包在一个特定的文件javax.annotation.processing.Processor到META-INF/services路径下，
 * 每一个元素换行分割
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年2月16日 下午11:24:54
 *
 */
@SupportedAnnotationTypes({
// 合法注解全名的集合
})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class DemoProcessor extends AbstractProcessor {
	// /*
	// * 必须指定，这个注解处理器是注册给哪个注解的。注意，它的返回值是一个字符串的集合，包含本处理器想要处理的注解类型的合法全称。换句话说，
	// * 你在这里定义你的注解处理器注册到哪些注解上。
	// *
	// * since Java 7,use @SupportedAnnotationTypes.
	// * 因为兼容的原因，特别是针对Android平台，建议使用重载getSupportedAnnotationTypes
	// * ()和getSupportedSourceVersion
	// * ()方法代替@SupportedAnnotationTypes和@SupportedSourceVersion。
	// */
	// @Override
	// public Set<String> getSupportedAnnotationTypes() {
	// return super.getSupportedAnnotationTypes();
	// }

	// /*
	// *
	// 用来指定你使用的Java版本。通常这里返回SourceVersion.latestSupported()。然而，如果你有足够的理由只支持Java
	// * 6的话，你也可以返回SourceVersion.RELEASE_6。推荐使用前者。
	// *
	// * since Java 7, use @SupportedSourceVersion
	// * 因为兼容的原因，特别是针对Android平台，建议使用重载getSupportedAnnotationTypes
	// * ()和getSupportedSourceVersion
	// * ()方法代替@SupportedAnnotationTypes和@SupportedSourceVersion。
	// */
	// @Override
	// public SourceVersion getSupportedSourceVersion() {
	// return super.getSupportedSourceVersion();
	// }

	/*
	 * 每一个注解处理器类都必须有一个空的构造函数。然而，这里有一个特殊的init()方法，它会被注解处理工具调用，
	 * 并输入ProcessingEnviroment参数。ProcessingEnviroment提供很多有用的工具类Elements,
	 * Types和Filer。
	 */
	@Override
	public synchronized void init(final ProcessingEnvironment processingEnv) {
		super.init(processingEnv);
	}

	/*
	 * 每个处理器的主函数main()。你在这里写你的扫描、评估和处理注解的代码，以及生成Java文件。输入参数RoundEnviroment，
	 * 可以让你查询出包含特定注解的被注解元素。
	 */
	@Override
	public boolean process(final Set<? extends TypeElement> annotations,
			final RoundEnvironment roundEnv) {
		System.out.println("DemoProcessor process");
		return false;
	}

}
