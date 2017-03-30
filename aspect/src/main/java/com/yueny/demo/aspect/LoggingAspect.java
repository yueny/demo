package com.yueny.demo.aspect;

import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年3月17日 下午4:01:25
 *
 */
@Aspect
@Order(2)
@Component
public class LoggingAspect {
	/**
	 * 后置通知：在目标方法执行之后（无论是否发生异常），执行该通知<br>
	 * 在后置通知中还不能访问目标方法执行的结果。执行结果可以在返回通知中访问。
	 */
	@After("execution(public * com.yueny.demo.aspect.service.PersonService.*(..))")
	public void afterMethod(final JoinPoint joinPoint) {
		final String methodName = joinPoint.getSignature().getName();
		System.out.println("end to " + methodName);
	}

	// 需要告诉这个方法需要在哪些类的哪些方法开始之前执行，所以加一个注解@before，作为前置通知
	// 声明该方法是一个前置通知：在目标方法之前执行
	@Before("execution(public * com.yueny.demo.aspect.service.PersonService.*(..))")
	public void beforeMethod(final JoinPoint joinPoint) {
		final String methodName = joinPoint.getSignature().getName();
		final List<Object> args = Arrays.asList(joinPoint.getArgs());
		System.out.println("begin to " + methodName + " with " + args);
	}

	/*
	 * 回环通知需要携带ProceedingJoinPoint类型参数
	 * 回环通知类似于动态代理的全过程：ProceedingJoinPoint类型的参数可以决定是否执行目标方法
	 * 且回环通知必须有返回值，返回值即为目标方法的返回值
	 */
	@Around("execution(public * com.yueny.demo.aspect.service.PersonService.*(..))")
	public Object roundingMethod(final ProceedingJoinPoint pjp) {
		Object result = null;
		final String methodName = pjp.getSignature().getName();

		try {
			System.out.println("Around: Begin Method" + methodName + " executed with " + Arrays.asList(pjp.getArgs()));

			// 前置通知
			result = pjp.proceed();

			// 返回通知
			System.out.println("Around: Return Method" + methodName + "  with result" + result);
		} catch (final Throwable e) {
			e.printStackTrace();
			// 异常通知
			System.out.println("Around: Exception in " + methodName + ":" + e);

		}

		// 后置通知
		System.out.println("Around: end Method" + methodName);

		return result;
	}
}
