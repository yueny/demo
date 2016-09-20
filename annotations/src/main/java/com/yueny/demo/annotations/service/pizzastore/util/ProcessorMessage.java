package com.yueny.demo.annotations.service.pizzastore.util;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

/**
 * This is a simple static helper class to print error, waring or note messages
 * during annotation processing.
 * <p>
 * You <b>must</b> initialize this class by calling
 * {@link #init(ProcessingEnvironment)} before you can use the messaging methods
 * </p>
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年2月22日 下午10:24:26
 *
 */
public class ProcessorMessage {
	private static ProcessingEnvironment processingEnvironment;

	/**
	 * Prints an error message
	 *
	 * @param e
	 *            The element which has caused the error. Can be null
	 * @param msg
	 *            The error message
	 */
	public static void error(final Element element, String message,
			final Object... args) {
		if (args.length > 0) {
			message = String.format(message, args);
		}
		// Kind.ERROR 用来表示我们的注解处理器处理失败了
		processingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR,
				message, element);
	}

	public static void init(final ProcessingEnvironment processingEnv) {
		processingEnvironment = processingEnv;
	}

	public static void note(final Element element, String message,
			final Object... args) {
		if (args.length > 0) {
			message = String.format(message, args);
		}
		processingEnvironment.getMessager().printMessage(Diagnostic.Kind.NOTE,
				message, element);
	}

	public static void warn(final Element element, String message,
			final Object... args) {
		if (args.length > 0) {
			message = String.format(message, args);
		}
		processingEnvironment.getMessager().printMessage(
				Diagnostic.Kind.WARNING, message, element);
	}

	private ProcessorMessage(final ProcessingEnvironment processingEnvironment) {
		this.processingEnvironment = processingEnvironment;
	}
}
