package com.yueny.demo.lamdba.listener;

/**
 * 单方法的单入参无返回值 监听器
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年6月21日 下午10:15:29
 *
 */
public interface Listener {
	/**
	 * 动作
	 */
	void action(String type);
}
