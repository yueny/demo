package com.yueny.demo.exec.sharevariable.generator;

import com.yueny.demo.common.annotion.UnSafe;
import com.yueny.demo.exec.sharevariable.EventChecker;

@UnSafe
public class EvenGenerator extends AbstractIntGenerator {
	public static void main(final String[] args) {
		EventChecker.test(new EvenGenerator());
	}

	private int currentEvenValue = 0;

	@Override
	public int next() {
		// public synchronized int next() {
		++currentEvenValue;
		++currentEvenValue;
		return currentEvenValue;
	}

}
