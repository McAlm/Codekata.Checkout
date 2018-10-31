package de.mcalm.codekata.checkout;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import de.mcalm.codekata.checkout.api.PricingRules;

public class Checkout {

	private Map<String, Integer> basket;
	private PricingRules pricingRules;

	public Checkout(PricingRules calculator) {
		this.pricingRules = calculator;
		this.basket = new HashMap<String, Integer>();
	}

	public void scanItem(String product, Integer amount) {
		this.basket.merge(product, amount, Integer::sum);
		Logger.getGlobal().info("scanned " + amount + " product(s) of " + product + ". Now " + basket.get(product)
				+ " of " + product + " in basket.");
	}

	public Map<String, Integer> getBasket() {
		return this.basket;
	}

	@SuppressWarnings("unchecked")
	public Double calculateTotals() {

		System.out.println("----");
		Double overall = this.basket.entrySet().stream()
				.map(e -> this.getPriceForItem(
						(Map<Integer, Double>) pricingRules.getRuleset().getPriceRules(e.getKey()), e.getValue()))
				.collect(Collectors.summingDouble(d -> d));


		System.out.println("++++ " + overall);
		return overall;

	}

	private Double getPriceForItem(Map<Integer, Double> prices, Integer amount) {
		ItemPrice ip = new ItemPrice(0.0, amount);

		ip = prices.entrySet().stream()
				.sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
				.reduce(ip, (i, e) -> calculateForItem(i, e), (a, b) -> a.setPrice(a.price + b.price));

		
		return ip.getPrice();
	}

	public ItemPrice calculateForItem(ItemPrice ip, Map.Entry<Integer, Double> entry) {

		int priceAmount = entry.getKey();
		double price = entry.getValue();

		if (ip.amount < priceAmount) {
			return ip;
		}

		if (ip.amount % priceAmount == 0) {
			ip.setPrice(ip.price + (ip.amount / priceAmount) * price);
			ip.setAmount(0);
			return ip;
		} else {
			int tempAmount = ip.amount % priceAmount;
			ip.setPrice(ip.price + ((ip.amount - tempAmount) / priceAmount) * price);
			ip.setAmount(tempAmount);
			return ip;
		}
	}

	private class ItemPrice {
		double price;
		int amount;

		public ItemPrice(double price, int amount) {
			super();
			this.price = price;
			this.amount = amount;
		}

		public double getPrice() {
			return price;
		}

		public ItemPrice setPrice(double price) {
			this.price = price;
			return this;
		}

		public int getAmount() {
			return amount;
		}

		public ItemPrice setAmount(int amount) {
			this.amount = amount;
			return this;
		}

	}

}
