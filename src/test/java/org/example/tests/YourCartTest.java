package org.example.tests;

import org.example.tests.base.BaseTest;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class YourCartTest extends BaseTest {

    @Test
    public void testCartItemDetails() {
        System.out.println("📋 Testing cart item details...");

        loginAsStandardUser();
        productsPage.addProductToCart("sauce-labs-backpack");
        productsPage.addProductToCart("sauce-labs-bike-light");
        productsPage.goToCart();

        // Verify items in cart
        assertTrue(cartPage.isItemInCart("Sauce Labs Backpack"),
                "Backpack should be in cart");
        assertTrue(cartPage.isItemInCart("Sauce Labs Bike Light"),
                "Bike Light should be in cart");
        assertEquals(cartPage.getCartItemCount(), 2,
                "Should have 2 items in cart");

        System.out.println("✅ Cart item details are correct");
    }

    @Test
    public void testRemoveItemFromCartPage() {
        System.out.println("📋 Testing item removal from cart page...");

        loginAsStandardUser();
        productsPage.addProductToCart("sauce-labs-backpack");
        productsPage.addProductToCart("sauce-labs-bike-light");
        productsPage.goToCart();

        // Remove one item
        cartPage.removeItem("Sauce Labs Backpack");

        assertEquals(cartPage.getCartItemCount(), 1,
                "Should have 1 item after removal");
        assertFalse(cartPage.isItemInCart("Sauce Labs Backpack"),
                "Backpack should be removed");
        assertTrue(cartPage.isItemInCart("Sauce Labs Bike Light"),
                "Bike Light should still be in cart");

        System.out.println("✅ Item removal from cart page works correctly");
    }

    @Test
    public void testLockedUserCannotAccessCart() {
        System.out.println("📋 Testing locked user access...");

        // Try to login as locked user
        loginPage.login("locked_out_user", "secret_sauce");

        assertTrue(loginPage.isErrorMessageDisplayed(),
                "Error message should be displayed");
        assertTrue(loginPage.getErrorMessage().contains("locked out"),
                "Should show locked out message");

        System.out.println("✅ Locked user cannot access cart");
    }

    @Test
    public void testCartIsolationBetweenUsers() {
        System.out.println("📋 Testing cart isolation between users...");

        // User 1 adds items
        loginAsStandardUser();
        productsPage.addProductToCart("sauce-labs-backpack");
        logout();

        // User 2 logs in
        loginAsProblemUser();

        // User 2 should have empty cart
        assertEquals(productsPage.getCartBadgeCount(), "0",
                "Different user should have empty cart");
        System.out.println("✅ Cart isolation works correctly");
    }
}