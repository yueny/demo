/*
 * Copyright (C) 2015 Hannes Dorfmann
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yueny.demo.annotations.service.pizzastore.processor;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;

import lombok.Getter;

import org.apache.commons.lang3.StringUtils;

import com.yueny.demo.annotations.service.pizzastore.annotation.Factory;

/**
 * 数据模型<br>
 * 保存被注解类的数据，比如合法的类的名字，以及@Factory注解本身的一些信息<br>
 * Holds the information about a class annotated with @Factory<br>
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年2月17日 上午11:35:55
 *
 */
public class FactoryAnnotatedClass {
	private final TypeElement annotatedClassElement;
	/**
	 * 获取在{@link Factory#id()}中指定的id
	 */
	@Getter
	private final String id;
	/**
	 * 获取在{@link Factory#type()}指定的类型合法全名
	 */
	@Getter
	private String qualifiedFactoryGroupName;
	/**
	 * 获取在{@link Factory#type()}{@link Factory#type()}指定的类型的简单名字
	 */
	@Getter
	private String simpleFactoryGroupName;

	/**
	 * @throws ProcessingException
	 *             if id() from annotation is null
	 */
	public FactoryAnnotatedClass(final TypeElement classElement)
			throws ProcessingException {
		this.annotatedClassElement = classElement;
		final Factory annotation = classElement.getAnnotation(Factory.class);
		// Read the id value (like "Calzone" or "Tiramisu")
		id = annotation.id();

		if (StringUtils.isEmpty(id)) {
			throw new ProcessingException(
					classElement,
					"id() in @%s for class %s is null or empty! that's not allowed",
					Factory.class.getSimpleName(), classElement
							.getQualifiedName().toString());
		}

		// Get the full QualifiedTypeName.获取@Fractory注解中的type成员
		try {
			// 这个类已经被编译
			final Class<?> clazz = annotation.type();
			qualifiedFactoryGroupName = clazz.getCanonicalName();
			simpleFactoryGroupName = clazz.getSimpleName();
		} catch (final MirroredTypeException mte) {
			// 这个还没有被编译：这种情况是我们尝试编译被@Fractory注解的源代码。
			// 这种情况下，直接获取Class会抛出MirroredTypeException异常。MirroredTypeException包含一个TypeMirror，它表示我们未编译类。
			// 因为我们已经知道它必定是一个类类型（我们已经在前面检查过），我们可以直接强制转换为DeclaredType，然后读取TypeElement来获取合法的名字。
			final DeclaredType classTypeMirror = (DeclaredType) mte
					.getTypeMirror();
			final TypeElement classTypeElement = (TypeElement) classTypeMirror
					.asElement();
			qualifiedFactoryGroupName = classTypeElement.getQualifiedName()
					.toString();
			simpleFactoryGroupName = classTypeElement.getSimpleName()
					.toString();
		}
	}

	/**
	 * 获取被@Factory注解的原始元素<br>
	 * The original element that was annotated with @Factory<br>
	 */
	public TypeElement getTypeElement() {
		return annotatedClassElement;
	}
}
