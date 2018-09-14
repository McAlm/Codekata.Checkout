package de.mcalm.codekata.checkout;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import de.mcalm.codekata.checkout.api.PricingRules;

public class Checkout {

	private Map<String, Integer> basket;
	private PricingRules pricingRules;

	public Checkout(PricingRules calculator) {
		this.pricingRules = calculator;
		this.basket = new HashMap<String, Integer>();
	}

	public void scanItem(String product, Integer amount) {
		this.basket.merge(product, amount, Integer::sum);
		Logger.getGlobal().info("scanned " + amount + " product(s) of " + product + ". Now " + basket.get(product) + " of " + product + " in basket.");
	}

	public Map<String, Integer> getBasket() {
		return this.basket;
	}

	public Double calculateTotals() {
		return this.basket.entrySet().stream().map((e) -> {
			return this.pricingRules.calculate(e.getKey(), e.getValue());
		}).mapToDouble(Double::doubleValue).sum();
	}

}
