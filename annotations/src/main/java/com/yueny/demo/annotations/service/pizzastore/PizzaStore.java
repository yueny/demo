package com.yueny.demo.annotations.service.pizzastore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.yueny.demo.annotations.service.pizzastore.factory.IMeal;

/**
 * 披萨店PizzsStore注解方式
 *
 * @author yueny09 <deep_blue_yang@163.com>
 *
 * @DATE 2016年2月17日 上午10:51:24
 *
 */
public class PizzaStore {
	public static void main(final String[] args) throws IOException {
		final PizzaStore pizzaStore = new PizzaStore();
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

	private final MealFactory factory = new MealFactory();

	/**
	 * 消费者需要输入餐(Meal)的名字
	 *
	 * @param mealName
	 *            餐名
	 * @return
	 */
	public IMeal order(final String mealName) {
		return factory.create(mealName);
	}
}
