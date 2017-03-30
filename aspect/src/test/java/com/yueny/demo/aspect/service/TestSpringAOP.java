package com.yueny.demo.aspect.service;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpringAOP {
	@Test
	public void testSpringAOP() {
		final ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:/config/all.xml");

		final PersonService personService = ac.getBean(PersonService.class);

		System.out.println(personService.getClass().getName());

		// final int result = personService.save("aaaa");
		// System.out.println(result);
		//
		// final String result2 = personService.getPersonName(33);
		// System.out.println(result2);

		final int result3 = personService.update("aaaa", 33);
		System.out.println(result3);

	}
}
