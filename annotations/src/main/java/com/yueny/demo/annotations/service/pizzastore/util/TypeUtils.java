package com.yueny.demo.annotations.service.pizzastore.util;

import java.io.IOException;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年2月22日 下午10:22:31
 *
 */
public class TypeUtils {

	/**
	 * Get the Package name
	 *
	 * @throws IOException
	 */
	public static String getPackageName(final Elements elementUtils,
			final TypeElement type) throws IOException {
		final PackageElement pkg = elementUtils.getPackageOf(type);
		if (!pkg.isUnnamed()) {
			return pkg.getQualifiedName().toString();
		} else {
			return ""; // Default package
		}
	}

	/**
	 * Checks if a element is of the type
	 */
	public static boolean isTypeOf(final Element element, final Class<?> clazz) {
		return isTypeOf(element.asType(), clazz);
	}

	/**
	 * Checks if a TypeMirror equals a class
	 */
	public static boolean isTypeOf(final TypeMirror type, final Class<?> clazz) {
		return type.toString().equals(clazz.getCanonicalName());
	}

	/**
	 * Returns the class this element is part of
	 */
	public static Class<?> isTypeOf(final TypeMirror type,
			final List<Class<?>> classList) {

		for (final Class<?> c : classList) {
			if (isTypeOf(type, c)) {
				return c;
			}
		}

		return null;
	}
}
