package com.yueny.demo.exec.atom1;

import com.yueny.demo.annotion.UnSafe;

@UnSafe
public class SerialNumberGenerator {
	private static volatile int serialNumber = 0;

	// synchronized
	public static int nextSerialNumber() {
		// un safe
		return serialNumber++;
	}
}
