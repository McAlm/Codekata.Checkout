package de.mcalm.codekata.checkout;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IntegrationTest {

	@Test
	public void test() {
		String json = "{\"A\":{\"1\":50.0, \"3\":130.0}, \"B\":{\"1\":30.0, \"2\":45.0},\"C\":{\"1\":20.0},\"D\":{\"1\":15.0}}";
		Checkout co = new Checkout(new DefaultPricingRules(() -> {
			return new RuleSet(JsonUtil.fromJson(json));
		}));
		co.scanItem("A", 1);
		co.scanItem("A", 1);
		co.scanItem("B", 2);
		co.scanItem("A", 3);
		co.scanItem("B", 2);
		co.scanItem("C", 3);
		co.scanItem("B", 1);
		co.scanItem("D", 2);
		assertEquals("\nShould calculate: \n" + 
		" (5A) 1*130.0 + 2*50  \n" +
		" (5B) 2*45.0 + 1*30.0 \n" + 
		" (3C) 3*20.0 \n" +
		" (2D) 2*15.0 \n" + 
		" Total: 440", 440.0, co.calculateTotals());
	}

}
