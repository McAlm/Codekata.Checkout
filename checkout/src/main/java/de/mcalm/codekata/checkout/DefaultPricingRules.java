package de.mcalm.codekata.checkout;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public class DefaultPricingRules implements PricingRules {

	private RuleSet ruleSet;

	public DefaultPricingRules(Supplier<RuleSet> rulesSupplier) {
		this.ruleSet = rulesSupplier.get();
	}

	@Override
	public Double calculate(String item, Integer amount) {
		
		Map<Integer, Double> pricesForItem = this.ruleSet.getPricesForItem(item);

		Optional<Integer> maxDiscountStep = pricesForItem.keySet().stream().max(Integer::compare);
		return calculateInternal(maxDiscountStep.get(), amount, pricesForItem);
	}

	/**
	 * recursive method to calculate the overall amount for one item depending on
	 * the amount of this item in the basket
	 * 
	 * @param discountStep
	 * @param amount
	 * @param pricesForItem
	 * @return
	 */
	private Double calculateInternal(Integer discountStep, Integer amount, Map<Integer, Double> pricesForItem) {
		if (discountStep == 0)
			return 0.0;
		if (amount > discountStep) {
			return ((int) (amount / discountStep)) * Optional.ofNullable(pricesForItem.get(discountStep)).orElse(0.0)
					+ calculateInternal(discountStep - 1, amount % discountStep, pricesForItem);

		} else if (amount == discountStep && pricesForItem.get(discountStep) != null) {
			return pricesForItem.get(discountStep);

		} else {
			return calculateInternal(discountStep - 1, amount, pricesForItem);
		}
	}

}
