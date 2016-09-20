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

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import com.google.auto.service.AutoService;
import com.yueny.demo.annotations.service.pizzastore.annotation.Factory;
import com.yueny.demo.annotations.service.pizzastore.util.FieldUtils;
import com.yueny.demo.annotations.service.pizzastore.util.ProcessorMessage;

/**
 * 注解处理器<br>
 * Annotation Processor for @Factory annotation
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年2月17日 上午11:05:09
 *
 */
@AutoService(Processor.class)
// AutoService注解处理器是,用来生成META-INF/services/javax.annotation.processing.Processor文件.
public class FactoryProcessor extends AbstractProcessor {

	/**
	 * 用来处理Element的工具类<br>
	 * Element代表程序的元素，例如包、类或者方法。每个Element代表一个静态的、语言级别的构件。<br>
	 * 在下面的例子中，我们通过注释来说明这个： <br>
	 * package com.example; // PackageElement<br>
	 * public class Foo { // TypeElement,包括class和interface。<br>
	 * private int a; // VariableElement <br>
	 * public Foo () {} // ExecuteableElement<br>
	 * public void setA ( // ExecuteableElement <br>
	 * int newA // TypeElement <br>
	 * ) {...}
	 */
	private Elements elementUtils;
	private final Map<String, FactoryGroupedClasses> factoryClasses = new LinkedHashMap<String, FactoryGroupedClasses>();
	/**
	 * 使用Filer创建文件
	 */
	private Filer filer;
	// private Messager messager;
	/**
	 * 用来处理TypeMirror的工具类
	 */
	private Types typeUtils;

	/*
	 * 本处理器将处理@Factory注解.
	 * 
	 * 或者使用 @SupportedAnnotationTypes({
	 * "com.yueny.demo.annotations.service.pizzastore.annotation.Factory"})
	 */
	@Override
	public Set<String> getSupportedAnnotationTypes() {
		final Set<String> annotataions = new LinkedHashSet<String>();
		annotataions.add(Factory.class.getCanonicalName());
		return annotataions;
	}

	/*
	 * 或者使用 @SupportedSourceVersion(SourceVersion.RELEASE_8)
	 */
	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.latestSupported();
	}

	/*
	 * .
	 */
	@Override
	public synchronized void init(final ProcessingEnvironment processingEnv) {
		super.init(processingEnv);

		typeUtils = processingEnv.getTypeUtils();
		elementUtils = processingEnv.getElementUtils();
		filer = processingEnv.getFiler();
		ProcessorMessage.init(processingEnv);
		// messager = processingEnv.getMessager();
	}

	/*
	 * 每个处理器的主函数main()。你在这里写你的扫描、评估和处理注解的代码
	 */
	@Override
	public boolean process(final Set<? extends TypeElement> annotations,
			final RoundEnvironment roundEnv) {
		try {
			// Scan classes:遍历所有被注解了@Factory的元素的列表(Element可以是类、方法、变量等)
			for (final Element annotatedElement : roundEnv
					.getElementsAnnotatedWith(Factory.class)) {
				// Check if a class has been annotated with
				// @Factory:检查被注解为@Factory的元素是否是一个类,以确保只有class元素被我们的处理器处理
				// 在注解处理器中，我们要避免使用instanceof，而是配合TypeMirror使用EmentKind或者TypeKind
				if (!FieldUtils.isClass(annotatedElement)) {
					// 只有class类才可以被@Factory注解
					throw new ProcessingException(annotatedElement,
							"Only classes can be annotated with @%s",
							Factory.class.getSimpleName());
				}

				// 因为我们已经知道它是ElementKind.CLASS类型，所以可以直接强制转换。We can cast it,
				// because we know that it of ElementKind.CLASS
				final TypeElement typeElement = (TypeElement) annotatedElement;

				final FactoryAnnotatedClass annotatedClass = new FactoryAnnotatedClass(
						typeElement);

				// 验证规则
				checkValidClass(annotatedClass);

				// 一切OK，组合被注解的类，添加FactoryAnnotatedClass到对应的FactoryGroupedClasses中。
				// Everything is fine, so try to add
				FactoryGroupedClasses factoryClass = factoryClasses
						.get(annotatedClass.getQualifiedFactoryGroupName());
				if (factoryClass == null) {
					final String qualifiedGroupName = annotatedClass
							.getQualifiedFactoryGroupName();
					factoryClass = new FactoryGroupedClasses(qualifiedGroupName);
					factoryClasses.put(qualifiedGroupName, factoryClass);
				}

				// 如果和其他的@Factory标注的类的id相同冲突，抛出IdAlreadyUsedException异常。Checks
				// if id is conflicting with another @Factory annotated class
				// with the same id
				factoryClass.add(annotatedClass);
			}

			// 代码生成。Generate code
			for (final FactoryGroupedClasses factoryClass : factoryClasses
					.values()) {
				factoryClass.generateJavaCode(elementUtils, filer);
			}
			// 清除factoryClasses
			factoryClasses.clear();
		} catch (final ProcessingException e) {
			ProcessorMessage.error(e.getElement(), e.getMessage());
		} catch (final IOException e) {
			ProcessorMessage.error(null, e.getMessage());
		}

		return true;
	}

	/**
	 * Checks if the annotated element observes our rules。<br>
	 *
	 * 1、必须是公开类：classElement.getModifiers().contains(Modifier.PUBLIC)。<br>
	 * 2、必须是非抽象类：classElement.getModifiers().contains(Modifier.ABSTRACT)。<br>
	 * 3、必须是@Factoy.type()指定的类型的子类或者接口的实现：<br>
	 * 首先我们使用elementUtils.getTypeElement(item.getQualifiedFactoryGroupName())
	 * 创建一个传入的Class
	 * (@Factoy.type())的元素。是的，你可以仅仅通过已知的合法类名来直接创建TypeElement（使用TypeMirror）。<br>
	 * 接下来我们检查它是一个接口还是一个类：superClassElement.getKind() == ElementKind.INTERFACE。<br>
	 * 所以我们这里有两种情况： <br>
	 * 如果是接口，就判断classElement.getInterfaces().contains(superClassElement.asType()
	 * )；<br>
	 * 如果是类，我们就必须使用currentClass.getSuperclass()扫描继承层级。 <br>
	 * 注意，整个检查也可以使用typeUtils.isSubtype()来实现。
	 * 4、类必须有一个公开的默认构造函数：我们遍历所有的闭元素classElement.getEnclosedElements()，<br>
	 * 然后检查ElementKind.CONSTRUCTOR、Modifier.PUBLIC以及constructorElement.
	 * getParameters().size() == 0。
	 */
	private void checkValidClass(final FactoryAnnotatedClass item)
			throws ProcessingException {
		// 转换为TypeElement, 含有更多特定的方法。Cast to TypeElement, has more type specific
		// methods
		final TypeElement classElement = item.getTypeElement();

		// 检查是否不是一个公共类
		if (!classElement.getModifiers().contains(Modifier.PUBLIC)) {
			throw new ProcessingException(classElement,
					"The class %s is not public.", classElement
							.getQualifiedName().toString());
		}

		// 检查是否是一个抽象类。Check if it's an abstract class
		if (classElement.getModifiers().contains(Modifier.ABSTRACT)) {
			throw new ProcessingException(
					classElement,
					"The class %s is abstract. You can't annotate abstract classes with @%",
					classElement.getQualifiedName().toString(), Factory.class
							.getSimpleName());
		}

		// 检查继承关系: 必须是@Factory.type()指定的类型子类。Check inheritance: Class must be
		// childclass as specified in @Factory.type();
		final TypeElement superClassElement = elementUtils.getTypeElement(item
				.getQualifiedFactoryGroupName());

		if (superClassElement.getKind() == ElementKind.INTERFACE) {
			// 检查接口是否实现了 。Check interface implemented
			if (!classElement.getInterfaces().contains(
					superClassElement.asType())) {
				throw new ProcessingException(
						classElement,
						"The class %s annotated with @%s must implement the interface %s",
						classElement.getQualifiedName().toString(),
						Factory.class.getSimpleName(), item
								.getQualifiedFactoryGroupName());
			}
		} else {
			// 检查子类。Check subclassing
			TypeElement currentClass = classElement;
			while (true) {
				final TypeMirror superClassType = currentClass.getSuperclass();

				if (superClassType.getKind() == TypeKind.NONE) {
					// // 到达了基本类型(java.lang.Object), 所以退出。Basis class
					// (java.lang.Object) reached, so exit
					throw new ProcessingException(
							classElement,
							"The class %s annotated with @%s must inherit from %s",
							classElement.getQualifiedName().toString(),
							Factory.class.getSimpleName(), item
									.getQualifiedFactoryGroupName());
				}

				// 找到了要求的父类。Required super class found
				if (superClassType.toString().equals(
						item.getQualifiedFactoryGroupName())) {
					break;
				}

				// 未找到，在继承树上继续向上搜寻。Moving up in inheritance tree
				currentClass = (TypeElement) typeUtils
						.asElement(superClassType);
			}
		}

		// 检查是否提供了默认公开构造函数。Check if an empty public constructor is given
		for (final Element enclosed : classElement.getEnclosedElements()) {
			if (enclosed.getKind() == ElementKind.CONSTRUCTOR) {
				final ExecutableElement constructorElement = (ExecutableElement) enclosed;
				if (constructorElement.getParameters().size() == 0
						&& constructorElement.getModifiers().contains(
								Modifier.PUBLIC)) {
					// 找到了默认构造函数。Found an empty constructor
					return;
				}
			}
		}

		// 没有找到默认构造函数。 No empty constructor found
		throw new ProcessingException(
				classElement,
				"The class %s must provide an public empty default constructor",
				classElement.getQualifiedName().toString());
	}

}
