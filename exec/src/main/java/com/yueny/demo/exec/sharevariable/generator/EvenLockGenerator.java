package com.yueny.demo.exec.sharevariable.generator;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.yueny.demo.common.annotion.Safe;
import com.yueny.demo.exec.sharevariable.EventChecker;

@Safe
public class EvenLockGenerator extends AbstractIntGenerator {
	public static void main(final String[] args) {
		EventChecker.test(new EvenLockGenerator());
	}

	private int currentEvenValue = 0;
	private final Lock lock = new ReentrantLock();

	@Override
	public int next() {
		lock.lock();
		try {
			++currentEvenValue;
			++currentEvenValue;
			// return语句必须在try中实现，以确保unlock()不会过早的发生
			return currentEvenValue;
		} finally {
			lock.unlock();
		}
	}

}
