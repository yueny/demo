package com.yueny.demo.lamdba;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年6月21日 下午10:06:22
 *
 */
public abstract class BaseService {
	/**
	 * Logger available to subclasses.
	 */
	protected final Logger logger = LoggerFactory.getLogger(getClass());
}
