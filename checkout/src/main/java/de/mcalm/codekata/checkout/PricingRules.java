package de.mcalm.codekata.checkout;

public interface PricingRules {

	public Double calculate(String item, Integer amount);

}
