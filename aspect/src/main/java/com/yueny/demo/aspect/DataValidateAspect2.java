package com.yueny.demo.aspect;

import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataValidateAspect2 {
	@Before("execution(public * com.yueny.demo.aspect.service.PersonService.*(..))")
	public boolean beforeMethod(final JoinPoint joinPoint) {
		final String methodName = joinPoint.getSignature().getName();
		final List<Object> args = Arrays.asList(joinPoint.getArgs());
		System.out.println("data validate 2---begin to " + methodName + " with " + args);
		return true;
	}
}
