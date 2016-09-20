package com.yueny.demo.annotations.service.listener;

import com.yueny.demo.annotations.annotation.ActionListenerFor;
import com.yueny.demo.annotations.service.listener.impl.CancelListener;
import com.yueny.demo.annotations.service.listener.impl.OkListener;

/**
 * 可以加在拦截器中
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年2月16日 下午9:12:52
 *
 */
public class ActionListenerService {
	@ActionListenerFor(listener = CancelListener.class)
	public void cancel() {

	}

	/**
	 * 使用注解设置Listener
	 */
	@ActionListenerFor(listener = OkListener.class)
	public void ok() {

	}

	public void run() {
		// 使得注解生效
		try {
			ActionListenerInstaller.install(new ActionListenerService());
		} catch (IllegalAccessException | InstantiationException e) {
			e.printStackTrace(System.out);
		}
	}
}
