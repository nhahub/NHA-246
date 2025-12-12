package org.example.tests;

import org.example.tests.base.BaseTest;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class CheckoutTest extends BaseTest {

    @Test
    public void testCompleteCheckoutProcess() {
        System.out.println("💰 Testing complete checkout process...");

        // Login and add product
        loginAsStandardUser();
        productsPage.addProductToCart("sauce-labs-backpack");
        productsPage.goToCart();

        // Proceed to checkout
        cartPage.proceedToCheckout();

        // Fill shipping information
        checkoutPage.enterShippingInfo("John", "Doe", "12345");
        checkoutPage.continueToOverview();
        checkoutPage.completePurchase();

        // Verify success
        assertTrue(checkoutPage.isCheckoutComplete(),
                "Checkout should be completed successfully");
        assertEquals(checkoutPage.getConfirmationMessage(),
                "THANK YOU FOR YOUR ORDER",
                "Confirmation message should be correct");
        System.out.println("✅ Checkout completed successfully");
    }

    @Test
    public void testCheckoutWithMultipleItems() {
        System.out.println("💰 Testing checkout with multiple items...");

        loginAsStandardUser();
        productsPage.addProductToCart("sauce-labs-backpack");
        productsPage.addProductToCart("sauce-labs-bike-light");
        productsPage.goToCart();

        cartPage.proceedToCheckout();
        checkoutPage.enterShippingInfo("Alice", "Smith", "54321");
        checkoutPage.continueToOverview();

        // Verify total price is displayed
        String total = checkoutPage.getTotalPrice();
        assertTrue(total.contains("Total"),
                "Total price should be displayed");
        System.out.println("✅ Checkout with multiple items: " + total);
    }

    @Test
    public void testCheckoutValidation() {
        System.out.println("💰 Testing checkout validation...");

        loginAsStandardUser();
        productsPage.addProductToCart("sauce-labs-backpack");
        productsPage.goToCart();
        cartPage.proceedToCheckout();

        // Try to continue without filling info
        checkoutPage.continueToOverview();

        // Should stay on checkout page
        assertTrue(checkoutPage.isCheckoutPageDisplayed(),
                "Should not proceed without required info");
        System.out.println("✅ Checkout validation works correctly");
    }
}