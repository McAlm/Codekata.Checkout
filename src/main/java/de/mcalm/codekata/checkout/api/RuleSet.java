package de.mcalm.codekata.checkout.api;

/**
 * A RuleSet contains the rules to calculate prices for items
 * @author wiese
 *
 * @param <T> The type of the data structure for all items
 * @param <P> The type of the data structure for one item
 */
public interface RuleSet<T, P> {

	public T getPriceRules();

	public P getPriceRules(String item);

	public void setPriceRules(T t);

}
