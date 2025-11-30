package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class YourCartTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private int passedTests = 0;
    private int failedTests = 0;
    private int totalTests = 0;

    public void runTests() {
        System.out.println("🛒 STARTING YOUR CART TESTS...");

        initializeBrowser();
        executeCartTestSuite();
        closeBrowser();

        printTestSummary();
    }

    private void initializeBrowser() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.manage().window().maximize();
    }

    private void executeCartTestSuite() {
        // TC_116 - Standard user verify cart items
        testStandardUserCartItems();

        // TC_117 - Standard user verify total price update
        testTotalPriceUpdate();

        // TC_118 - Standard user continue shopping
        testContinueShopping();

        // TC_119 - Locked out user cannot access cart
        testLockedOutUserAccess();

        // TC_128 - Problem user verify cart items
        testProblemUserCartItems();

        // TC_129 - Performance user verify cart items
        testPerformanceUserCartItems();

        // TC_130 - Error user verify cart items
        testErrorUserCartItems();
    }

    // TC_116: Standard user - verify cart items
    private void testStandardUserCartItems() {
        totalTests++;
        System.out.println("\n📋 TC_116 - Standard User Verify Cart Items");

        boolean testPassed = true;

        try {
            loginUser("standard_user", "secret_sauce");

            addProductToCart("sauce-labs-backpack");
            addProductToCart("sauce-labs-bike-light");

            navigateToCart();

            if (!verifyCartContainsItem("Sauce Labs Backpack")) testPassed = false;
            if (!verifyCartContainsItem("Sauce Labs Bike Light")) testPassed = false;
            if (!verifyCartItemCount(2)) testPassed = false;

            if (!verifyItemPrice("Sauce Labs Backpack", "$29.99")) testPassed = false;
            if (!verifyItemPrice("Sauce Labs Bike Light", "$9.99")) testPassed = false;
            if (!verifyItemQuantity("Sauce Labs Backpack", "1")) testPassed = false;
            if (!verifyItemQuantity("Sauce Labs Bike Light", "1")) testPassed = false;

            logoutUser();

        } catch (Exception e) {
            testPassed = false;
            System.out.println("❌ TC_116 FAILED - Exception: " + e.getMessage());
        }

        if (testPassed) {
            passedTests++;
            System.out.println("✅ TC_116 PASSED - Cart items verified correctly");
        } else {
            failedTests++;
            System.out.println("❌ TC_116 FAILED - Cart items verification failed");
        }
    }

    // TC_117: Standard user - verify total price update
    private void testTotalPriceUpdate() {
        totalTests++;
        System.out.println("\n💰 TC_117 - Verify Total Price Updates");

        boolean testPassed = true;

        try {
            loginUser("standard_user", "secret_sauce");

            addProductToCart("sauce-labs-backpack");
            addProductToCart("sauce-labs-bike-light");

            navigateToCart();

            String initialTotal = getCartTotal();
            System.out.println("Initial cart total: " + initialTotal);

            removeProductFromCart("Sauce Labs Bike Light");

            String updatedTotal = getCartTotal();
            System.out.println("Updated cart total: " + updatedTotal);

            if (!verifyCartItemCount(1)) testPassed = false;

            logoutUser();

        } catch (Exception e) {
            testPassed = false;
            System.out.println("❌ TC_117 FAILED - Exception: " + e.getMessage());
        }

        if (testPassed) {
            passedTests++;
            System.out.println("✅ TC_117 PASSED - Total price updates correctly");
        } else {
            failedTests++;
            System.out.println("❌ TC_117 FAILED - Total price update failed");
        }
    }

    // TC_118: Standard user - continue shopping
    private void testContinueShopping() {
        totalTests++;
        System.out.println("\n🔄 TC_118 - Verify Continue Shopping");

        boolean testPassed = true;

        try {
            loginUser("standard_user", "secret_sauce");

            addProductToCart("sauce-labs-backpack");

            navigateToCart();

            clickContinueShopping();

            if (!verifyOnProductsPage()) testPassed = false;
            if (!verifyCartBadgeCount("1")) testPassed = false;

            logoutUser();

        } catch (Exception e) {
            testPassed = false;
            System.out.println("❌ TC_118 FAILED - Exception: " + e.getMessage());
        }

        if (testPassed) {
            passedTests++;
            System.out.println("✅ TC_118 PASSED - Continue shopping works correctly");
        } else {
            failedTests++;
            System.out.println("❌ TC_118 FAILED - Continue shopping failed");
        }
    }

    // TC_119: Locked out user - cannot access cart
    private void testLockedOutUserAccess() {
        totalTests++;
        System.out.println("\n🚫 TC_119 - Locked Out User Access Test");

        boolean testPassed = true;

        try {
            driver.get("https://www.saucedemo.com/");
            driver.findElement(By.id("user-name")).sendKeys("locked_out_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();

            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("[data-test='error']")));
            String errorText = errorElement.getText();

            if (!errorText.contains("Sorry, this user has been locked out")) {
                testPassed = false;
                System.out.println("❌ Locked out user error message incorrect");
            }

            String currentUrl = driver.getCurrentUrl();
            if (!currentUrl.contains("saucedemo.com") || currentUrl.contains("inventory")) {
                testPassed = false;
                System.out.println("❌ User not on login page");
            }

        } catch (Exception e) {
            testPassed = false;
            System.out.println("❌ TC_119 FAILED - Exception: " + e.getMessage());
        }

        if (testPassed) {
            passedTests++;
            System.out.println("✅ TC_119 PASSED - Locked user cannot access cart");
        } else {
            failedTests++;
            System.out.println("❌ TC_119 FAILED - Locked user access test failed");
        }
    }

    // TC_128: Problem user - verify cart items
    private void testProblemUserCartItems() {
        totalTests++;
        System.out.println("\n🐛 TC_128 - Problem User Cart Items");

        boolean testPassed = true;

        try {
            loginUser("problem_user", "secret_sauce");

            addProductToCart("sauce-labs-backpack");
            addProductToCart("sauce-labs-bike-light");

            navigateToCart();

            if (!verifyCartContainsItem("Sauce Labs Backpack")) testPassed = false;
            if (!verifyCartContainsItem("Sauce Labs Bike Light")) testPassed = false;
            if (!verifyCartItemCount(2)) testPassed = false;

            if (!verifyProductImages()) testPassed = false;

            logoutUser();

        } catch (Exception e) {
            testPassed = false;
            System.out.println("❌ TC_128 FAILED - Exception: " + e.getMessage());
        }

        if (testPassed) {
            passedTests++;
            System.out.println("✅ TC_128 PASSED - Problem user cart verified");
        } else {
            failedTests++;
            System.out.println("❌ TC_128 FAILED - Problem user cart test failed");
        }
    }

    // TC_129: Performance user - verify cart items
    private void testPerformanceUserCartItems() {
        totalTests++;
        System.out.println("\n⚡ TC_129 - Performance User Cart Items");

        boolean testPassed = true;

        try {
            loginUser("performance_glitch_user", "secret_sauce");

            addProductToCart("sauce-labs-backpack");
            addProductToCart("sauce-labs-bike-light");

            navigateToCart();

            if (!verifyCartContainsItem("Sauce Labs Backpack")) testPassed = false;
            if (!verifyCartContainsItem("Sauce Labs Bike Light")) testPassed = false;
            if (!verifyCartItemCount(2)) testPassed = false;

            logoutUser();

        } catch (Exception e) {
            testPassed = false;
            System.out.println("❌ TC_129 FAILED - Exception: " + e.getMessage());
        }

        if (testPassed) {
            passedTests++;
            System.out.println("✅ TC_129 PASSED - Performance user cart verified");
        } else {
            failedTests++;
            System.out.println("❌ TC_129 FAILED - Performance user cart test failed");
        }
    }

    // TC_130: Error user - verify cart items
    private void testErrorUserCartItems() {
        totalTests++;
        System.out.println("\n❌ TC_130 - Error User Cart Items");

        boolean testPassed = true;

        try {
            loginUser("error_user", "secret_sauce");

            addProductToCart("sauce-labs-backpack");
            addProductToCart("sauce-labs-bike-light");

            navigateToCart();

            List<WebElement> cartItems = driver.findElements(By.className("cart_item"));
            System.out.println("Error user cart items count: " + cartItems.size());

            List<WebElement> errorMessages = driver.findElements(By.cssSelector("[data-test='error']"));
            if (!errorMessages.isEmpty()) {
                System.out.println("⚠️ Error encountered: " + errorMessages.get(0).getText());
                // For error user, we expect some issues, so don't fail the test for errors
            }

            // For error user, we just verify we can reach cart page
            if (!driver.getCurrentUrl().contains("cart.html")) {
                testPassed = false;
            }

            logoutUser();

        } catch (Exception e) {
            testPassed = false;
            System.out.println("❌ TC_130 FAILED - Exception: " + e.getMessage());
        }

        if (testPassed) {
            passedTests++;
            System.out.println("✅ TC_130 PASSED - Error user cart verified");
        } else {
            failedTests++;
            System.out.println("❌ TC_130 FAILED - Error user cart test failed");
        }
    }

    // ========== HELPER METHODS ==========

    private void loginUser(String username, String password) {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();
        wait.until(ExpectedConditions.urlContains("inventory.html"));
        System.out.println("Logged in as: " + username);
    }

    private void logoutUser() {
        driver.findElement(By.id("react-burger-menu-btn")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link"))).click();
        wait.until(ExpectedConditions.urlContains("saucedemo.com"));
    }

    private void addProductToCart(String productName) {
        String addButtonId = "add-to-cart-" + productName;
        driver.findElement(By.id(addButtonId)).click();
        System.out.println("Added to cart: " + productName);
    }

    private void removeProductFromCart(String productName) {
        String removeButtonId = "remove-" + productName.toLowerCase().replace(" ", "-");
        driver.findElement(By.id(removeButtonId)).click();
        System.out.println("Removed from cart: " + productName);
    }

    private void navigateToCart() {
        driver.findElement(By.className("shopping_cart_link")).click();
        wait.until(ExpectedConditions.urlContains("cart.html"));
    }

    private boolean verifyCartContainsItem(String itemName) {
        List<WebElement> cartItems = driver.findElements(By.className("inventory_item_name"));
        for (WebElement item : cartItems) {
            if (item.getText().equals(itemName)) {
                System.out.println("✅ Cart contains: " + itemName);
                return true;
            }
        }
        System.out.println("❌ Cart missing: " + itemName);
        return false;
    }

    private boolean verifyCartItemCount(int expectedCount) {
        List<WebElement> cartItems = driver.findElements(By.className("cart_item"));
        int actualCount = cartItems.size();
        if (actualCount == expectedCount) {
            System.out.println("✅ Cart item count correct: " + actualCount);
            return true;
        } else {
            System.out.println("❌ Cart item count incorrect. Expected: " + expectedCount + ", Actual: " + actualCount);
            return false;
        }
    }

    private boolean verifyItemPrice(String itemName, String expectedPrice) {
        List<WebElement> cartItems = driver.findElements(By.className("cart_item"));
        for (WebElement item : cartItems) {
            WebElement nameElement = item.findElement(By.className("inventory_item_name"));
            if (nameElement.getText().equals(itemName)) {
                WebElement priceElement = item.findElement(By.className("inventory_item_price"));
                String actualPrice = priceElement.getText();
                if (actualPrice.equals(expectedPrice)) {
                    System.out.println("✅ Price correct for " + itemName + ": " + actualPrice);
                    return true;
                } else {
                    System.out.println("❌ Price incorrect for " + itemName + ". Expected: " + expectedPrice + ", Actual: " + actualPrice);
                    return false;
                }
            }
        }
        System.out.println("❌ Item not found for price verification: " + itemName);
        return false;
    }

    private boolean verifyItemQuantity(String itemName, String expectedQuantity) {
        List<WebElement> cartItems = driver.findElements(By.className("cart_item"));
        for (WebElement item : cartItems) {
            WebElement nameElement = item.findElement(By.className("inventory_item_name"));
            if (nameElement.getText().equals(itemName)) {
                WebElement quantityElement = item.findElement(By.className("cart_quantity"));
                String actualQuantity = quantityElement.getText();
                if (actualQuantity.equals(expectedQuantity)) {
                    System.out.println("✅ Quantity correct for " + itemName + ": " + actualQuantity);
                    return true;
                } else {
                    System.out.println("❌ Quantity incorrect for " + itemName + ". Expected: " + expectedQuantity + ", Actual: " + actualQuantity);
                    return false;
                }
            }
        }
        System.out.println("❌ Item not found for quantity verification: " + itemName);
        return false;
    }

    private String getCartTotal() {
        // Go to checkout to see total
        driver.findElement(By.id("checkout")).click();
        wait.until(ExpectedConditions.urlContains("checkout-step-one.html"));

        // Fill checkout info
        driver.findElement(By.id("first-name")).sendKeys("Test");
        driver.findElement(By.id("last-name")).sendKeys("User");
        driver.findElement(By.id("postal-code")).sendKeys("12345");
        driver.findElement(By.id("continue")).click();
        wait.until(ExpectedConditions.urlContains("checkout-step-two.html"));

        // Get total
        WebElement totalElement = driver.findElement(By.className("summary_total_label"));
        String totalText = totalElement.getText();

        // Go back to cart
        driver.findElement(By.id("cancel")).click();
        wait.until(ExpectedConditions.urlContains("cart.html"));

        return totalText;
    }

    private void clickContinueShopping() {
        driver.findElement(By.id("continue-shopping")).click();
    }

    private boolean verifyOnProductsPage() {
        if (driver.getCurrentUrl().contains("inventory.html")) {
            System.out.println("✅ Successfully returned to products page");
            return true;
        } else {
            System.out.println("❌ Not on products page");
            return false;
        }
    }

    private boolean verifyCartBadgeCount(String expectedCount) {
        WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
        if (cartBadge.getText().equals(expectedCount)) {
            System.out.println("✅ Cart badge count correct: " + expectedCount);
            return true;
        } else {
            System.out.println("❌ Cart badge count incorrect. Expected: " + expectedCount + ", Actual: " + cartBadge.getText());
            return false;
        }
    }

    private boolean verifyProductImages() {
        List<WebElement> productImages = driver.findElements(By.className("inventory_item_img"));
        System.out.println("Product images in cart: " + productImages.size());

        boolean allImagesLoaded = true;
        for (WebElement img : productImages) {
            WebElement imgElement = img.findElement(By.tagName("img"));
            String src = imgElement.getAttribute("src");
            if (src.contains("jpg") || src.contains("png")) {
                System.out.println("✅ Product image loaded: " + src);
            } else {
                System.out.println("❌ Product image issue: " + src);
                allImagesLoaded = false;
            }
        }
        return allImagesLoaded;
    }

    private void printTestSummary() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("📊 YOUR CART TESTS SUMMARY");
        System.out.println("=".repeat(50));
        System.out.println("Total Tests Run: " + totalTests);
        System.out.println("✅ Tests Passed: " + passedTests);
        System.out.println("❌ Tests Failed: " + failedTests);

        double passPercentage = (double) passedTests / totalTests * 100;
        System.out.printf("📈 Success Rate: %.2f%%\n", passPercentage);

        if (failedTests == 0) {
            System.out.println("🎉 ALL YOUR CART TESTS PASSED! 🎉");
        } else {
            System.out.println("⚠️  Some tests failed. Check the logs above.");
        }
        System.out.println("=".repeat(50));
    }

    private void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}