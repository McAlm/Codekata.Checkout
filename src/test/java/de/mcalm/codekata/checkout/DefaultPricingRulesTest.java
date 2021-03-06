package de.mcalm.codekata.checkout;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import de.mcalm.codekata.checkout.impl.DefaultPricingRules;
import de.mcalm.codekata.checkout.impl.DefaultRuleSet;

public class DefaultPricingRulesTest {

	private DefaultPricingRules calculator;

	@Before
	public void setUp() {
		this.calculator = new DefaultPricingRules(this::getTestRules);
	}

	private DefaultRuleSet getTestRules() {
		Map<String, Map<Integer, Double>> prices = new HashMap<>();
		Map<Integer, Double> pricesA = new HashMap<>();
		pricesA.put(1, 10.0);
		pricesA.put(3, 25.0);
		pricesA.put(5, 45.0);

		prices.put("A", pricesA);

		return new DefaultRuleSet(prices);
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
	
	
	@Test
	public void testRecursionWithLambda() {
		Map<Integer, Double> map = this.getTestRules().getPriceRules("A");
		Integer amount = 8;
		
		Optional<Integer> max = map.keySet().stream().max(Integer::compare);
		IntStream.range(0, max.get()).boxed().forEach(i -> map.putIfAbsent(i,  0.0));
		
		System.out.println(map.size());
		
		Double collect = map.keySet().stream().sorted(Collections.reverseOrder(Integer::compare)).collect(Collectors.summingDouble(i -> amount ));
		System.out.println("Collected: " + collect);
		
	}
}
