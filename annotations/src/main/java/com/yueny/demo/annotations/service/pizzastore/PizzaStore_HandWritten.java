package com.yueny.demo.annotations.service.pizzastore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.yueny.demo.annotations.service.pizzastore.factory.CalzonePizza;
import com.yueny.demo.annotations.service.pizzastore.factory.IMeal;
import com.yueny.demo.annotations.service.pizzastore.factory.MargheritaPizza;
import com.yueny.demo.annotations.service.pizzastore.factory.Tiramisu;

/**
 * 披萨店PizzsStore传统方式
 * 
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年2月17日 上午10:51:51
 *
 */
public class PizzaStore_HandWritten {

	public static void main(final String[] args) throws IOException {
		final PizzaStore_HandWritten pizzaStore = new PizzaStore_HandWritten();
		final IMeal meal = pizzaStore.order(readConsole());
		System.out.println("Bill: $" + meal.getPrice());
	}

	private static String readConsole() throws IOException {
		System.out.println("What do you like?");
		final BufferedReader bufferRead = new BufferedReader(
				new InputStreamReader(System.in));
		final String input = bufferRead.readLine();
		return input;
	}

	/**
	 * 消费者需要输入餐(Meal)的名字
	 *
	 * @param mealName
	 *            餐名
	 * @return
	 */
	public IMeal order(final String mealName) {
		if (mealName == null) {
			throw new IllegalArgumentException("Name of the meal is null!");
		}

		if ("Margherita".equals(mealName)) {
			return new MargheritaPizza();
		}

		if ("Calzone".equals(mealName)) {
			return new CalzonePizza();
		}

		if ("Tiramisu".equals(mealName)) {
			return new Tiramisu();
		}

		throw new IllegalArgumentException("Unknown meal '" + mealName + "'");
	}

}
