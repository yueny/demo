package com.yueny.demo.annotations.service.listener.impl;

import com.yueny.demo.annotations.service.listener.ActionListener;

/**
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年2月16日 下午9:05:45
 *
 */
public class OkListener implements ActionListener {

	@Override
	public void action() {
		System.out.println("确认！！！");
	}

}
