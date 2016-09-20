package com.yueny.demo.annotations.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年2月16日 下午8:12:26
 */
@Documented
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DemoAnnotation {
	/**
	 * Documented 注解文档提取，表示是否将注解信息添加在java文档中。
	 */
	/**
	 * Target 注解修饰目标。如果不明确指出，该注解可以放在任何地方。<br>
	 * 需要说明的是：属性的注解是兼容的，如果你想给7个属性都添加注解，仅仅排除一个属性，那么你需要在定义target包含所有的属性。<br>
	 * ElementType.TYPE:用于描述类、接口或enum声明<br>
	 * ElementType.FIELD:用于描述实例变量<br>
	 * ElementType.METHOD<br>
	 * ElementType.PARAMETER<br>
	 * ElementType.CONSTRUCTOR<br>
	 * ElementType.LOCAL_VARIABLE<br>
	 * ElementType.ANNOTATION_TYPE 另一个注释<br>
	 * ElementType.PACKAGE 用于记录java文件的package信息<br>
	 */
	/**
	 * Retention 注解保留策略。<br>
	 * RetentionPolicy.SOURCE:在编译阶段丢弃。这些注解在编译结束之后就不再有任何意义， 所以它们不会写入字节码
	 * 。Override,SuppressWarnings都属于这类注解。<br>
	 * RetentionPolicy.CLASS:在类加载的时候丢弃。在字节码文件的处理中有用。注解默认使用这种方式。<br>
	 * RetentionPolicy.RUNTIME:始终不会丢弃，运行期也保留该注解，因此可以使用反射机制读取该注解的信息
	 * 。我们自定义的注解通常使用这种方式 。也就是说，只有当定义Annotation时使用了@Retention(RetentionPolicy
	 * .RUNTIME)修饰, JVM才会在装载class文件时提取保存在class文件中的Annotation,该Annotation才会在运行时可见
	 * ,这样我们才能够解析 <br>
	 */
	/**
	 * Inherited 注解继承声明.指定被修饰的Annotation将具有继承性<br>
	 * 如果某个类使用@Xxx注解(该Annotation使用了@Inherited修饰)修饰, 则其子类自动被@Xxx注解修饰.
	 */

	public enum Priority {
		HIGH, LOW, MEDIUM
	}

	/**
	 * @return 描述
	 */
	String desc() default "";

	/**
	 * @return 优先级
	 */
	Priority priority() default Priority.LOW;
}
