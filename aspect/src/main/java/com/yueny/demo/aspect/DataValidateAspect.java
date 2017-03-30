package com.yueny.demo.aspect;

import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年3月17日 下午4:15:25
 *
 */
@Order(Integer.MAX_VALUE)
@Aspect
@Component
public class DataValidateAspect {
	@Before("execution(public * com.yueny.demo.aspect.service.PersonService.*(..))")
	public boolean beforeMethod(final JoinPoint joinPoint) {
		final String methodName = joinPoint.getSignature().getName();
		final List<Object> args = Arrays.asList(joinPoint.getArgs());
		System.out.println("data validate---begin to " + methodName + " with " + args);
		return true;
	}

}
