package com.yueny.demo.annotations.service.pizzastore.util;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

/**
 * A collection of utils for class fields
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年2月22日 下午10:20:28
 *
 */
public class FieldUtils {

	/**
	 * Is the element a class?
	 */
	public static boolean isClass(final Element element) {
		return element.getKind() == ElementKind.CLASS;
	}

	/**
	 * Is the element a field?
	 */
	public static boolean isField(final Element element) {
		return element.getKind() == ElementKind.FIELD;
	}
}
