package com.yueny.demo.annotations.service.pizzastore.factory;

import com.yueny.demo.annotations.service.pizzastore.annotation.Factory;

/**
 * Calzone披萨
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年2月17日 上午10:44:32
 *
 */
@Factory(id = "Calzone", type = IMeal.class)
public class CalzonePizza implements IMeal {

	@Override
	public float getPrice() {
		return 8.5f;
	}
}
