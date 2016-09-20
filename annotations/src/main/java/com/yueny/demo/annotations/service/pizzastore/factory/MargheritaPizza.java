package com.yueny.demo.annotations.service.pizzastore.factory;

import com.yueny.demo.annotations.service.pizzastore.annotation.Factory;

/**
 * Margherita披萨
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年2月17日 上午10:44:48
 *
 */
@Factory(id = "Margherita", type = IMeal.class)
public class MargheritaPizza implements IMeal {

	@Override
	public float getPrice() {
		return 6f;
	}
}
