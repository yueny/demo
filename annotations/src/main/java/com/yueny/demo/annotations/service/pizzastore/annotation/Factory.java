package com.yueny.demo.annotations.service.pizzastore.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to indicate classes that are part of a certain factory<br>
 * 使用同样的type()注解那些属于同一个工厂的类，并且用注解的id()做一个映射
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年2月17日 上午10:48:39
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Factory {
	/**
	 * The identifier for determining which item should be instantiated。<br>
	 * 用来表示生成哪个对象的唯一id; <br>
	 * id只能是String类型，并且在同一个type组中必须唯一。
	 */
	String id();

	/**
	 * The name of the factory。<br>
	 * 被@Factory注解的类必须直接或者间接的继承于type()指定的类型；<br>
	 * 具有相同的type的注解类，将被聚合在一起生成一个工厂类。这个生成的类使用Factory后缀，例如type =
	 * Meal.class，将生成MealFactory工厂类；
	 */
	Class<?> type();

}
