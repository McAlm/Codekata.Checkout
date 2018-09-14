package de.mcalm.codekata.checkout;

import java.util.HashMap;
import java.util.Map;

public class Checkout {

	private Map<String, Integer> basket;
	private PricingRules calculator;

	public Checkout(PricingRules calculator) {
		this.calculator = calculator;
		this.basket = new HashMap<String, Integer>();
	}

	public void scanItem(String product, Integer amount) {
		this.basket.merge(product, amount, Integer::sum);
	}

	public Map<String, Integer> getBasket() {
		return this.basket;
	}

	public Double calculateTotals() {
		return this.basket.entrySet().stream().map((e) -> {
			return this.calculator.calculate(e.getKey(), e.getValue());
		}).mapToDouble(Double::doubleValue).sum();
	}

}
