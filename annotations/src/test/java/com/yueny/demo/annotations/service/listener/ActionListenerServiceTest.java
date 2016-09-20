package com.yueny.demo.annotations.service.listener;

import org.junit.Test;

public class ActionListenerServiceTest {
	ActionListenerService service = new ActionListenerService();

	@Test
	public void testMethodRun() {
		service.run();
	}
}
