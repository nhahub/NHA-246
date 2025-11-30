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

public class CartTest {
    WebDriver driver;
    WebDriverWait wait;

    public void runTests() {
        System.out.println("🛒 STARTING CART AUTOMATION TESTS...");

        try {
            setup();

            // تشغيل كل الـ test cases اللي عندك
            runAllCartTests();

        } catch (Exception e) {
            System.out.println("❌ Error in cart tests: " + e.getMessage());
        } finally {
            tearDown();
        }
    }

    private void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        driver.manage().window().maximize();
    }

    private void runAllCartTests() {
        // TC_047 - Add single product
        testSingleProductAdd();

        // TC_048 - Add multiple products
        testMultipleProductsAdd();

        // TC_049 - Remove product
        testProductRemove();

        // TC_050 - Add all products
        testAddAllProducts();

        // TC_055 - Cart persistence
        testCartPersistence();

        // TC_056 - Locked user
        testLockedUser();

        // TC_057 - Problem user add to cart
        testProblemUserCart();

        // TC_064 - Cross-user cart isolation
        testCartIsolation();

    }

    // TC_047: Add single product
    private void testSingleProductAdd() {
        System.out.println("\n🔍 TC_047 - Testing Single Product Add...");
        try {
            login("standard_user", "secret_sauce");

            // Add Backpack to cart
            WebElement backpack = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
            backpack.click();

            // Check cart badge
            WebElement cartBadge = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.className("shopping_cart_badge")));
            String badgeCount = cartBadge.getText();

            if (badgeCount.equals("1")) {
                System.out.println("✅ TC_047 PASSED - Cart badge shows '1'");
            } else {
                System.out.println("❌ TC_047 FAILED - Cart badge shows: " + badgeCount);
            }

            // Check button changed to Remove
            WebElement removeButton = driver.findElement(By.id("remove-sauce-labs-backpack"));
            if (removeButton.getText().contains("Remove")) {
                System.out.println("✅ TC_047 PASSED - Button changed to 'Remove'");
            }

            logout();

        } catch (Exception e) {
            System.out.println("❌ TC_047 FAILED - " + e.getMessage());
        }
    }

    // TC_048: Add multiple products
    private void testMultipleProductsAdd() {
        System.out.println("\n🔍 TC_048 - Testing Multiple Products Add...");
        try {
            login("standard_user", "secret_sauce");

            // Add 3 products
            driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
            driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();
            driver.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt")).click();

            // Check cart badge
            WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
            if (cartBadge.getText().equals("3")) {
                System.out.println("✅ TC_048 PASSED - Cart shows 3 items");
            }

            // Go to cart and verify products
            driver.findElement(By.className("shopping_cart_link")).click();
            List<WebElement> cartItems = driver.findElements(By.className("cart_item"));
            if (cartItems.size() == 3) {
                System.out.println("✅ TC_048 PASSED - All 3 products visible in cart");
            }

            // Go back to products
            driver.findElement(By.id("continue-shopping")).click();
            logout();

        } catch (Exception e) {
            System.out.println("❌ TC_048 FAILED - " + e.getMessage());
        }
    }

    // TC_049: Remove product
    private void testProductRemove() {
        System.out.println("\n🔍 TC_049 - Testing Product Remove...");
        try {
            login("standard_user", "secret_sauce");

            // Add product first
            driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

            // Remove product
            driver.findElement(By.id("remove-sauce-labs-backpack")).click();

            // Check cart badge is gone
            List<WebElement> cartBadges = driver.findElements(By.className("shopping_cart_badge"));
            if (cartBadges.isEmpty()) {
                System.out.println("✅ TC_049 PASSED - Product removed from cart");
            }

            logout();

        } catch (Exception e) {
            System.out.println("❌ TC_049 FAILED - " + e.getMessage());
        }
    }

    // TC_050: Add all products
    private void testAddAllProducts() {
        System.out.println("\n🔍 TC_050 - Testing Add All Products...");
        try {
            login("standard_user", "secret_sauce");

            // Add all 6 products
            String[] products = {
                    "add-to-cart-sauce-labs-backpack",
                    "add-to-cart-sauce-labs-bike-light",
                    "add-to-cart-sauce-labs-bolt-t-shirt",
                    "add-to-cart-sauce-labs-fleece-jacket",
                    "add-to-cart-sauce-labs-onesie",
                    "add-to-cart-test.allthethings()-t-shirt-(red)"
            };

            for (String product : products) {
                driver.findElement(By.id(product)).click();
            }

            // Check cart badge
            WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
            if (cartBadge.getText().equals("6")) {
                System.out.println("✅ TC_050 PASSED - All 6 products added to cart");
            }

            // Clear cart for next tests
            for (String product : products) {
                String removeId = product.replace("add-to-cart", "remove");
                if (driver.findElements(By.id(removeId)).size() > 0) {
                    driver.findElement(By.id(removeId)).click();
                }
            }

            logout();

        } catch (Exception e) {
            System.out.println("❌ TC_050 FAILED - " + e.getMessage());
        }
    }

    // TC_055: Cart persistence
    private void testCartPersistence() {
        System.out.println("\n🔍 TC_055 - Testing Cart Persistence...");
        try {
            login("standard_user", "secret_sauce");

            // Add products
            driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
            driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();

            // Logout and login again
            logout();
            login("standard_user", "secret_sauce");

            // Check cart
            WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
            if (cartBadge.getText().equals("2")) {
                System.out.println("✅ TC_055 PASSED - Cart items preserved after relogin");
            }

            // Clear cart
            driver.findElement(By.id("remove-sauce-labs-backpack")).click();
            driver.findElement(By.id("remove-sauce-labs-bike-light")).click();
            logout();

        } catch (Exception e) {
            System.out.println("❌ TC_055 FAILED - " + e.getMessage());
        }
    }

    // TC_056: Locked user
    private void testLockedUser() {
        System.out.println("\n🔍 TC_056 - Testing Locked User...");
        try {
            driver.get("https://www.saucedemo.com/");
            driver.findElement(By.id("user-name")).sendKeys("locked_out_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();

            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("[data-test='error']")));
            String errorText = errorElement.getText();

            if (errorText.contains("Sorry, this user has been locked out")) {
                System.out.println("✅ TC_056 PASSED - Locked user cannot login");
            } else {
                System.out.println("❌ TC_056 FAILED - Wrong error message: " + errorText);
            }

        } catch (Exception e) {
            System.out.println("❌ TC_056 FAILED - " + e.getMessage());
        }
    }

    // TC_057: Problem user add to cart
    private void testProblemUserCart() {
        System.out.println("\n🔍 TC_057 - Testing Problem User Cart...");
        try {
            login("problem_user", "secret_sauce");

            // Try to add product
            driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

            // Check if item was added (problem user might have issues)
            List<WebElement> cartBadges = driver.findElements(By.className("shopping_cart_badge"));
            if (!cartBadges.isEmpty()) {
                System.out.println("✅ TC_057 PASSED - Problem user can add to cart");
            } else {
                System.out.println("⚠️ TC_057 - Problem user cart behavior inconsistent");
            }

            logout();

        } catch (Exception e) {
            System.out.println("❌ TC_057 FAILED - " + e.getMessage());
        }
    }

    // TC_064: Cross-user cart isolation
    private void testCartIsolation() {
        System.out.println("\n🔍 TC_064 - Testing Cart Isolation...");
        try {
            // Login as user1 and add products
            login("standard_user", "secret_sauce");
            driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
            logout();

            // Login as user2 and check cart
            login("problem_user", "secret_sauce");
            List<WebElement> cartBadges = driver.findElements(By.className("shopping_cart_badge"));

            if (cartBadges.isEmpty()) {
                System.out.println("✅ TC_064 PASSED - Users have separate carts");
            } else {
                System.out.println("❌ TC_064 FAILED - Cart data mixed between users");
            }

            logout();

        } catch (Exception e) {
            System.out.println("❌ TC_064 FAILED - " + e.getMessage());
        }
    }

    private void login(String username, String password) {
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();
        wait.until(ExpectedConditions.urlContains("inventory.html"));
    }

    private void logout() {
        try {
            driver.findElement(By.id("react-burger-menu-btn")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link"))).click();
        } catch (Exception e) {
            // Ignore logout errors
        }
    }

    private void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}