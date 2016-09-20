package com.yueny.demo.lamdba.listener;

import com.yueny.demo.lamdba.enums.ActionType;

/**
 * 单方法的多入参无返回值 监听器
 * 
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年6月21日 下午10:15:19
 *
 */
public interface Listeners {
	/**
	 * 动作
	 */
	void action(ActionType type, String msg);
}
