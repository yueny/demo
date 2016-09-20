package com.yueny.demo.lamdba.listener;

import org.junit.Test;

import com.yueny.demo.lamdba.enums.ActionType;
import com.yueny.demo.lamdba.test.AbstractBaseSpringTest;

/**
 * 单方法的多入参无返回值 的landba表达式
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年6月21日 下午9:55:40
 *
 */
public class ListenersTest extends AbstractBaseSpringTest {
	@Test
	public void testAction() {
		for (final ActionType actionType : ActionType.values()) {
			final Listeners listeners = (type, msg) -> {
				logger.info("监听类型:{} 进行数据 {} 的操作!", actionType, msg);
			};

			ListenersProcess.reg(actionType, listeners);
		}

		ListenersProcess.run(ActionType.LOVE, "李玉");
		ListenersProcess.run(ActionType.LOVE, "我爱你");
	}
}
