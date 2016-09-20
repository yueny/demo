package com.yueny.demo.annotations.service.pizzastore.factory;

import com.yueny.demo.annotations.service.pizzastore.annotation.Factory;

/**
 * 提拉米苏甜点(Tiramisu)
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年2月17日 上午10:46:21
 *
 */
@Factory(id = "Tiramisu", type = IMeal.class)
public class Tiramisu implements IMeal {

	@Override
	public float getPrice() {
		return 4.5f;
	}
}
