package de.mcalm.codekata.checkout.api;

public interface PricingRules {

	/**
	 * returns the overall price for one item depending on the amount of this item
	 * in the shoppingcart
	 * 
	 * @param item
	 * @param amount
	 * @return
	 */
	public Double calculate(String item, Integer amount);

}
