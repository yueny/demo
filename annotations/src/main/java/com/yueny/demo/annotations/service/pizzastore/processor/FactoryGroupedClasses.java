package com.yueny.demo.annotations.service.pizzastore.processor;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.yueny.demo.annotations.service.pizzastore.annotation.Factory;

/**
 * 将简单的组合所有的FactoryAnnotatedClasses到一起<br>
 *
 * This class holds all {@link FactoryAnnotatedClass}s that belongs to one
 * factory. In other words, this class holds a list with all @Factory annotated
 * classes. This class also checks if the id of each @Factory annotated class is
 * unique.
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年2月17日 下午12:56:44
 *
 */
public class FactoryGroupedClasses {
	/**
	 * 将被添加到生成的工厂类的名字中<br>
	 * Will be added to the name of the generated factory class
	 */
	private static final String SUFFIX = "Factory";

	/**
	 * 映射@Factory.id()到FactoryAnnotatedClass
	 */
	private final Map<String, FactoryAnnotatedClass> itemsMap = new LinkedHashMap<>();

	private final String qualifiedClassName;

	public FactoryGroupedClasses(final String qualifiedClassName) {
		this.qualifiedClassName = qualifiedClassName;
	}

	/**
	 * Adds an annotated class to this factory.<br>
	 * 如果和其他的@Factory标注的类的id相同冲突，抛出IdAlreadyUsedException异常。
	 *
	 * @throws ProcessingException
	 *             if another annotated class with the same id is already
	 *             present.
	 */
	public void add(final FactoryAnnotatedClass toInsert)
			throws ProcessingException {
		final FactoryAnnotatedClass existing = itemsMap.get(toInsert.getId());
		if (existing != null) {
			// Alredy existing
			throw new ProcessingException(
					toInsert.getTypeElement(),
					"Conflict: The class %s is annotated with @%s with id ='%s' but %s already uses the same id",
					toInsert.getTypeElement().getQualifiedName().toString(),
					Factory.class.getSimpleName(), toInsert.getId(), existing
							.getTypeElement().getQualifiedName().toString());
		}

		itemsMap.put(toInsert.getId(), toInsert);
	}

	/**
	 * 生成工厂类代码
	 *
	 * @param elementUtils
	 * @param filer
	 * @throws IOException
	 */
	public void generateJavaCode(final Elements elementUtils, final Filer filer)
			throws IOException {
		final TypeElement superClassName = elementUtils
				.getTypeElement(qualifiedClassName);
		// 生成 IMealFactory.java
		final String factoryClassName = superClassName.getSimpleName() + SUFFIX;
		// final String qualifiedFactoryClassName = qualifiedClassName + SUFFIX;

		// 写包名
		final PackageElement pkg = elementUtils.getPackageOf(superClassName);
		final String packageName = pkg.isUnnamed() ? null : pkg
				.getQualifiedName().toString();

		final MethodSpec.Builder method = MethodSpec.methodBuilder("create")
				.addModifiers(Modifier.PUBLIC).addParameter(String.class, "id")
				.returns(TypeName.get(superClassName.asType()));

		// check if id is null
		method.beginControlFlow("if (id == null)")
				.addStatement("throw new IllegalArgumentException($S)",
						"id is null!").endControlFlow();

		// Generate items map
		for (final FactoryAnnotatedClass item : itemsMap.values()) {
			method.beginControlFlow("if ($S.equals(id))", item.getId())
					.addStatement("return new $L()",
							item.getTypeElement().getQualifiedName().toString())
					.endControlFlow();
		}

		method.addStatement("throw new IllegalArgumentException($S + id)",
				"Unknown id = ");

		final TypeSpec typeSpec = TypeSpec.classBuilder(factoryClassName)
				.addMethod(method.build()).build();

		// Write file
		JavaFile.builder(packageName, typeSpec).build().writeTo(filer);
	}

	/**
	 * Generate the java code
	 *
	 * @throws IOException
	 *
	 *             public void generateCode(Elements elementUtils, Filer filer)
	 *             throws IOException {
	 *
	 *             TypeElement superClassName =
	 *             elementUtils.getTypeElement(qualifiedClassName);
	 *
	 *             String factoryClassName = superClassName.getSimpleName() +
	 *             SUFFIX;
	 *
	 *             JavaFileObject jfo =
	 *             filer.createSourceFile(qualifiedClassName + SUFFIX);
	 *
	 *             Writer writer = jfo.openWriter();
	 *
	 *             JavaWriter jw = new JavaWriter(writer);
	 *
	 *             // 写包名。Write package
	 *
	 *             PackageElement pkg =
	 *             elementUtils.getPackageOf(superClassName); if
	 *             (!pkg.isUnnamed()) {
	 *             jw.emitPackage(pkg.getQualifiedName().toString());
	 *             jw.emitEmptyLine(); } else { jw.emitPackage(""); }
	 *
	 *             jw.beginType(factoryClassName, "class",
	 *             EnumSet.of(Modifier.PUBLIC)); jw.emitEmptyLine();
	 *             jw.beginMethod(qualifiedClassName, "create",
	 *             EnumSet.of(Modifier.PUBLIC), "String", "id");
	 *
	 *             jw.beginControlFlow("if (id == null)");
	 *             jw.emitStatement("throw new IllegalArgumentException(\"id is null!\")"
	 *             ); jw.endControlFlow();
	 *
	 *             for (FactoryAnnotatedClass item : itemsMap.values()) {
	 *             jw.beginControlFlow("if (\"%s\".equals(id))", item.getId());
	 *             jw.emitStatement("return new %s()",
	 *             item.getTypeElement().getQualifiedName().toString());
	 *             jw.endControlFlow(); jw.emitEmptyLine(); }
	 *
	 *             jw.emitStatement(
	 *             "throw new IllegalArgumentException(\"Unknown id = \" + id)"
	 *             ); jw.endMethod();
	 *
	 *             jw.endType();
	 *
	 *             jw.close(); }
	 */
}
