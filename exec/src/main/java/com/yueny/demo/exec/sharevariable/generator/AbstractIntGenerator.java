package com.yueny.demo.exec.sharevariable.generator;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年1月24日 下午8:01:14
 *
 */
public abstract class AbstractIntGenerator {
	private volatile boolean canceled = false;

	/**
	 * allow this to be canceled
	 */
	public void cancel() {
		canceled = true;
	}

	public boolean isCanceled() {
		return canceled;
	}

	public abstract int next();

}
