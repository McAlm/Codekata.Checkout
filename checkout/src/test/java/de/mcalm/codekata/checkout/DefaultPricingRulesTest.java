package de.mcalm.codekata.checkout;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class DefaultPricingRulesTest {

	private DefaultPricingRules calculator;

	@Before
	public void setUp() {
		this.calculator = new DefaultPricingRules(this::getTestRules);
	}

	private RuleSet getTestRules() {
		Map<String, Map<Integer, Double>> prices = new HashMap<>();
		Map<Integer, Double> pricesA = new HashMap<>();
		pricesA.put(1, 10.0);
		pricesA.put(3, 25.0);
		pricesA.put(5, 45.0);

		prices.put("A", pricesA);

		return new RuleSet(prices);
	}

	@Test
	public void testCalculate1A() {
		assertEquals("Should sum: 1 * 10.0", 10.0, this.calculator.calculate("A", 1));
		assertEquals("Should sum: 2 * 10.0", 20.0, this.calculator.calculate("A", 2));
		assertEquals("Should sum: 1 * 25.0", 25.0, this.calculator.calculate("A", 3));
		assertEquals("Should sum: 1 * 45.0", 45.0, this.calculator.calculate("A", 5));
		assertEquals("Should sum: 1 * 45.0 + 1 * 10.0", 55.0, this.calculator.calculate("A", 6));
		assertEquals("Should sum: 1 * 45.0 + 2 * 10.0", 65.0, this.calculator.calculate("A", 7));
		assertEquals("Should sum: 3 * 45.0 + 2 * 10.0", 155.0, this.calculator.calculate("A", 17));
		assertEquals("Should sum: 3 * 45.0 + 1 * 25.0 + 1 * 10.0", 170.0, this.calculator.calculate("A", 19));
	}
}
