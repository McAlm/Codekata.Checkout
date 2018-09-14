package de.mcalm.codekata.checkout;

import java.util.HashMap;
import java.util.Map;

public class RuleSet {

	private Map<String, Map<Integer, Double>> rules = new HashMap<String, Map<Integer, Double>>();

	public RuleSet(Map<String, Map<Integer, Double>> rules) {
		this.rules = rules;
	}

	public Map<Integer, Double> getPricesForItem(String item) {
		return this.rules.get(item);
	}
}