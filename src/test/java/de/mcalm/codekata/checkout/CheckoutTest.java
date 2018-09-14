package de.mcalm.codekata.checkout;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import de.mcalm.codekata.checkout.api.PricingRules;

public class CheckoutTest {

	private Checkout co;
	private PricingRules calculator;

	@Before
	public void setUp() throws Exception {
		this.calculator = EasyMock.createMock(PricingRules.class);
		this.co = new Checkout(calculator);
	}

	@Test
	public void testScanItemAddsProductToBasket() {
		assertScannedItem(co, "A", 1, 1);
		assertScannedItem(co, "A", 1, 2);
		assertScannedItem(co, "B", 2, 2);
		assertScannedItem(co, "A", 3, 5);
	}

	private void assertScannedItem(Checkout co, String product, Integer amount, Integer expectedAmount) {
		co.scanItem(product, amount);
		assertEquals(expectedAmount, co.getBasket().get(product));
	}

	@Test
	public void testCalculateTotalCallsCalculatorWithRuleSet() {
		this.co.scanItem("A", 1);
		this.co.scanItem("B", 1);
		this.co.scanItem("C", 1);
		EasyMock.expect(this.calculator.calculate("A", 1)).andReturn(new Double(10.0));
		EasyMock.expect(this.calculator.calculate("B", 1)).andReturn(new Double(20.0));
		EasyMock.expect(this.calculator.calculate("C", 1)).andReturn(new Double(30.0));
		EasyMock.replay(this.calculator);
		
		assertEquals (60.0, this.co.calculateTotals());
		EasyMock.verify(this.calculator);
	}
}
