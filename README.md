# Codekata.Checkout
A codekata which calculates all items in a shopping cart at checkout.

The project has a strong focus on decoupling. It was developed mainly by using TDD. It is quite flexible to replace the pricing rules and the according calculation implementation by introducing another PricingRule implementation. Also so project offers the flexibility to replace the mechanism where to load pricing rules from at the initialisation of a checkout process. Currently there is a fixed Map used for tests and for the App a json String is parsed. Later on it could be extended by using a database, in e.g. This was achieved by using a Supplier<RuleSet> which is in charge to provide the pricing rules data.

The project sturcture looks like:
src/de.mcalm.codekata.checkout
- api: contains interface declarations
- impl: contains the default implementation used for this kata
- util: contains utility class to parse json (not in focus for this project)
- Checkout.java: the actual Checkout process implementation
- App.java: main class to demonstrate the usage

test/de.mcalm.codekata.checkout
- CheckoutTest.java: uses Mock objects to mock the dependencies to PricingRules
- DefaultPricingRulesTest.java: tests the required algorithm for this kata
- IntegrationTest.java: putting it all together and verifies the calculated overall amount

The project requires Maven and Java 1.8.
