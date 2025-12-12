package org.example.tests;

import org.example.tests.base.BaseTest;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class CartTest extends BaseTest {

    @Test
    public void testAddSingleProductToCart() {
        System.out.println("🛒 Testing single product addition...");

        loginAsStandardUser();
        productsPage.addProductToCart("sauce-labs-backpack");

        assertEquals(productsPage.getCartBadgeCount(), "1",
                "Cart should show 1 item");
        System.out.println("✅ Single product added successfully");
    }

    @Test
    public void testAddMultipleProducts() {
        System.out.println("🛒 Testing multiple products addition...");

        loginAsStandardUser();
        productsPage.addProductToCart("sauce-labs-backpack");
        productsPage.addProductToCart("sauce-labs-bike-light");
        productsPage.addProductToCart("sauce-labs-bolt-t-shirt");

        assertEquals(productsPage.getCartBadgeCount(), "3",
                "Cart should show 3 items");

        productsPage.goToCart();
        assertEquals(cartPage.getCartItemCount(), 3,
                "Cart page should show 3 items");
        System.out.println("✅ Multiple products added successfully");
    }

    @Test
    public void testRemoveProductFromCart() {
        System.out.println("🛒 Testing product removal...");

        loginAsStandardUser();
        productsPage.addProductToCart("sauce-labs-backpack");
        productsPage.removeProductFromCart("sauce-labs-backpack");

        // Cart badge should not be displayed
        assertFalse(productsPage.isCartBadgeDisplayed(),
                "Cart badge should disappear after removal");
        System.out.println("✅ Product removed successfully");
    }

    @Test
    public void testCartPersistence() {
        System.out.println("🔄 Testing cart persistence after logout/login...");

        loginAsStandardUser();
        productsPage.addProductToCart("sauce-labs-backpack");
        productsPage.addProductToCart("sauce-labs-bike-light");

        logout();
        loginAsStandardUser();

        assertEquals(productsPage.getCartBadgeCount(), "2",
                "Cart items should persist after relogin");
        System.out.println("✅ Cart persistence works correctly");
    }

    @Test
    public void testContinueShopping() {
        System.out.println("🔄 Testing continue shopping...");

        loginAsStandardUser();
        productsPage.addProductToCart("sauce-labs-backpack");
        productsPage.goToCart();

        cartPage.continueShopping();

        assertTrue(productsPage.isProductsPageDisplayed(),
                "Should return to products page");
        assertEquals(productsPage.getCartBadgeCount(), "1",
                "Cart badge should still show 1 item");
        System.out.println("✅ Continue shopping works correctly");
    }
}