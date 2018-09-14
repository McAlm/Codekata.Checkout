package de.mcalm.codekata.checkout;

import java.util.logging.Logger;

import de.mcalm.codekata.checkout.impl.DefaultPricingRules;
import de.mcalm.codekata.checkout.impl.DefaultRuleSet;
import de.mcalm.codekata.checkout.util.JsonUtil;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		Logger.getGlobal().info("Starte Checkout...");
		String json = "{\"A\":{\"1\":50.0, \"3\":130.0}, \"B\":{\"1\":30.0, \"2\":45.0},\"C\":{\"1\":20.0},\"D\":{\"1\":15.0}}";
		Checkout co = new Checkout(new DefaultPricingRules(() -> {
			return new DefaultRuleSet(JsonUtil.fromJson(json));
		}));
		co.scanItem("A", 1);
		co.scanItem("A", 1);
		co.scanItem("B", 2);
		co.scanItem("A", 3);
		co.scanItem("C", 3);
		Double calculateTotals = co.calculateTotals();
		Logger.getGlobal().info("Total amount: " + calculateTotals);
	}
}
