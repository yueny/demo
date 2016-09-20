package com.yueny.demo.annotations.service.pizzastore;

import com.yueny.demo.annotations.service.pizzastore.factory.CalzonePizza;
import com.yueny.demo.annotations.service.pizzastore.factory.IMeal;
import com.yueny.demo.annotations.service.pizzastore.factory.MargheritaPizza;
import com.yueny.demo.annotations.service.pizzastore.factory.Tiramisu;

/**
 * 披萨工厂。该工厂由factoryClass.generateJavaCode生成
 */
public class MealFactory {
	public IMeal create(final String id) {
		if (id == null) {
			throw new IllegalArgumentException("id is null!");
		}
		if ("Calzone".equals(id)) {
			return new CalzonePizza();
		}

		if ("Tiramisu".equals(id)) {
			return new Tiramisu();
		}

		if ("Margherita".equals(id)) {
			return new MargheritaPizza();
		}

		throw new IllegalArgumentException("Unknown id = " + id);
	}

}
