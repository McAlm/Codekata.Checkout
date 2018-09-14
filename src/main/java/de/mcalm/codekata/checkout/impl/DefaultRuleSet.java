package de.mcalm.codekata.checkout.impl;

import java.util.HashMap;
import java.util.Map;

import de.mcalm.codekata.checkout.api.RuleSet;

/**
 * A RuleSet contains the prices and discounts for all products
 * 
 * @author wiese
 *
 */
public class DefaultRuleSet implements RuleSet<Map<String, Map<Integer, Double>>, Map<Integer, Double>> {

	private Map<String, Map<Integer, Double>> rules = new HashMap<String, Map<Integer, Double>>();

	public DefaultRuleSet(Map<String, Map<Integer, Double>> rules) {
		this.rules = rules;
	}

	public Map<Integer, Double> getPriceRules(String item) {
		return this.rules.get(item);
	}

	@Override
	public Map<String, Map<Integer, Double>> getPriceRules() {
		return this.rules;
	}

	@Override
	public void setPriceRules(Map<String, Map<Integer, Double>> t) {
		this.rules = t;
	}

}