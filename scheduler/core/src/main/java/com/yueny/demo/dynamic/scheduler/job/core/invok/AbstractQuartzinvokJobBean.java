package com.yueny.demo.dynamic.scheduler.job.core.invok;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.yueny.demo.dynamic.scheduler.job.core.DynamicInvokJob;
import com.yueny.demo.dynamic.scheduler.job.core.jobbean.AbstractQuartzJobBean;

/**
 * 抽象Job 类，提供反射服务
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2017年2月7日 下午6:29:30
 *
 */
public abstract class AbstractQuartzinvokJobBean extends AbstractQuartzJobBean {
	/**
	 * 通过反射调用scheduleJob中定义的方法
	 *
	 * @param scheduleJob
	 */
	public void invokMethod(final DynamicInvokJob scheduleJob) {
		Object object = null;
		Class<?> clazz = null;
		try {
			clazz = Class.forName(scheduleJob.getTarget());
			object = clazz.newInstance();
		} catch (final Exception e) {
			e.printStackTrace();
		}

		if (object == null) {
			logger.error("任务名称 = [{}]---------------未启动成功，请检查是否配置正确！！！", scheduleJob.getJobName());
			return;
		}

		clazz = object.getClass();
		Method method = null;
		try {
			method = clazz.getDeclaredMethod(scheduleJob.getMethodName());
		} catch (final NoSuchMethodException e) {
			logger.error("任务名称 = [" + scheduleJob.getJobName() + "]---------------未启动成功，方法名设置错误！！！");
			e.printStackTrace();
		} catch (final SecurityException e) {
			e.printStackTrace();
		}

		if (method != null) {
			try {
				method.invoke(object);
			} catch (final IllegalAccessException e) {
				e.printStackTrace();
			} catch (final IllegalArgumentException e) {
				e.printStackTrace();
			} catch (final InvocationTargetException e) {
				e.printStackTrace();
			}
		}

	}

}
