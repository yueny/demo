package com.yueny.demo.exec.sharevariable.generator;

import java.util.concurrent.atomic.AtomicInteger;

import com.yueny.demo.annotion.Safe;
import com.yueny.demo.exec.sharevariable.EventChecker;

@Safe
public class AtomicEvenGenerator extends AbstractIntGenerator {
	public static void main(final String[] args) {
		EventChecker.test(new AtomicEvenGenerator());
	}

	private final AtomicInteger currentEvenValue = new AtomicInteger(0);

	@Override
	public int next() {
		return currentEvenValue.addAndGet(2);
	}

}
