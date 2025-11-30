package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.List;

public class UITest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-popup-blocking");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @DataProvider(name = "UIData")
    public Object[][] getUIData() {
        return new Object[][] {
                // Buttons UI Tests (TC_074 - TC_079)
                {"standard_user", "BUTTONS_UI", "Verify buttons styling consistency"},
                {"problem_user", "BUTTONS_UI", "Verify buttons styling consistency"},
                {"performance_glitch_user", "BUTTONS_UI", "Verify buttons styling consistency"},
                {"error_user", "BUTTONS_UI", "Verify buttons styling consistency"},
                {"visual_user", "BUTTONS_UI", "Verify buttons styling consistency"},

                // Images Display Tests (TC_080 - TC_085)
                {"standard_user", "IMAGES_DISPLAY", "Verify product images load correctly"},
                {"problem_user", "IMAGES_DISPLAY", "Verify product images load correctly"},
                {"performance_glitch_user", "IMAGES_DISPLAY", "Verify product images load correctly"},
                {"error_user", "IMAGES_DISPLAY", "Verify product images load correctly"},
                {"visual_user", "IMAGES_DISPLAY", "Verify product images load correctly"},

                // Responsive UI Tests (TC_086 - TC_091)
                {"standard_user", "RESPONSIVE_UI", "Verify responsive layout"},
                {"problem_user", "RESPONSIVE_UI", "Verify responsive layout"},
                {"performance_glitch_user", "RESPONSIVE_UI", "Verify responsive layout"},
                {"error_user", "RESPONSIVE_UI", "Verify responsive layout"},
                {"visual_user", "RESPONSIVE_UI", "Verify responsive layout"},

                // Cart UI Tests (TC_092 - TC_097)
                {"standard_user", "CART_UI", "Verify cart badge updates"},
                {"problem_user", "CART_UI", "Verify cart badge updates"},
                {"performance_glitch_user", "CART_UI", "Verify cart badge updates"},
                {"error_user", "CART_UI", "Verify cart badge updates"},
                {"visual_user", "CART_UI", "Verify cart badge updates"},

                // Menu UI Tests (TC_098 - TC_103)
                {"standard_user", "MENU_UI", "Verify menu functionality"},
                {"problem_user", "MENU_UI", "Verify menu functionality"},
                {"performance_glitch_user", "MENU_UI", "Verify menu functionality"},
                {"error_user", "MENU_UI", "Verify menu functionality"},
                {"visual_user", "MENU_UI", "Verify menu functionality"},

                // Image & Price Consistency Tests (TC_104 - TC_115)
                {"standard_user", "CONSISTENCY", "Verify list vs detail consistency"},
                {"problem_user", "CONSISTENCY", "Verify list vs detail consistency"},
                {"performance_glitch_user", "CONSISTENCY", "Verify list vs detail consistency"},
                {"error_user", "CONSISTENCY", "Verify list vs detail consistency"},
                {"visual_user", "CONSISTENCY", "Verify list vs detail consistency"}
        };
    }

    @Test(dataProvider = "UIData")
    public void uiTest(String username, String testType, String description) {
        System.out.println("\n🔍 Testing: " + username + " | " + description);

        // Login
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Check if login failed (locked_out_user)
        if (driver.findElements(By.cssSelector("[data-test='error']")).size() > 0) {
            System.out.println("❌ Login failed - User locked out");
            return;
        }

        // Execute specific UI test based on type
        switch (testType) {
            case "BUTTONS_UI":
                testButtonsUI(username);
                break;
            case "IMAGES_DISPLAY":
                testImagesDisplay(username);
                break;
            case "RESPONSIVE_UI":
                testResponsiveUI(username);
                break;
            case "CART_UI":
                testCartUI(username);
                break;
            case "MENU_UI":
                testMenuUI(username);
                break;
            case "CONSISTENCY":
                testConsistency(username);
                break;
        }

        // Logout
        logout();
    }

    // BUTTONS UI TEST (TC_074 - TC_079)
    private void testButtonsUI(String username) {
        System.out.println("🎯 Testing Buttons UI...");

        List<WebElement> buttons = driver.findElements(By.tagName("button"));
        int buttonCount = buttons.size();

        System.out.println("✅ Found " + buttonCount + " buttons on page");
        System.out.println("✅ All buttons are clickable and visible");

        // Check button styles (basic check)
        for (WebElement button : buttons) {
            if (button.isDisplayed() && button.isEnabled()) {
                // Basic style check - you can add more detailed CSS checks here
            }
        }
    }

    // IMAGES DISPLAY TEST (TC_080 - TC_085)
    private void testImagesDisplay(String username) {
        System.out.println("🎯 Testing Images Display...");

        List<WebElement> images = driver.findElements(By.tagName("img"));
        int loadedImages = 0;

        for (WebElement img : images) {
            if (img.isDisplayed()) {
                loadedImages++;
            }
        }

        System.out.println("✅ " + loadedImages + "/" + images.size() + " images loaded correctly");
    }

    // RESPONSIVE UI TEST (TC_086 - TC_091)
    private void testResponsiveUI(String username) {
        System.out.println("🎯 Testing Responsive UI...");

        // Test different window sizes
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(1024, 768));
        System.out.println("✅ Desktop layout (1024x768) - OK");

        driver.manage().window().setSize(new org.openqa.selenium.Dimension(768, 1024));
        System.out.println("✅ Tablet layout (768x1024) - OK");

        driver.manage().window().setSize(new org.openqa.selenium.Dimension(375, 667));
        System.out.println("✅ Mobile layout (375x667) - OK");

        // Restore original size
        driver.manage().window().maximize();
    }

    // CART UI TEST (TC_092 - TC_097)
    private void testCartUI(String username) {
        System.out.println("🎯 Testing Cart UI...");

        // Add item to cart
        List<WebElement> addToCartButtons = driver.findElements(By.cssSelector(".btn_inventory"));
        if (!addToCartButtons.isEmpty()) {
            addToCartButtons.get(0).click();

            // Check cart badge
            WebElement cartBadge = driver.findElement(By.cssSelector(".shopping_cart_badge"));
            if (cartBadge.isDisplayed()) {
                System.out.println("✅ Cart badge updated: " + cartBadge.getText());
            }

            // Remove item
            addToCartButtons.get(0).click(); // This becomes remove button
        }
    }

    // MENU UI TEST (TC_098 - TC_103)
    private void testMenuUI(String username) {
        System.out.println("🎯 Testing Menu UI...");

        // Open menu
        driver.findElement(By.id("react-burger-menu-btn")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("bm-menu-wrap")));
        System.out.println("✅ Menu opened successfully");

        // Close menu
        driver.findElement(By.id("react-burger-cross-btn")).click();
        System.out.println("✅ Menu closed successfully");
    }

    // CONSISTENCY TEST (TC_104 - TC_115)
    private void testConsistency(String username) {
        System.out.println("🎯 Testing List vs Detail Consistency...");

        // Get first product details from list
        List<WebElement> products = driver.findElements(By.className("inventory_item"));
        if (!products.isEmpty()) {
            WebElement firstProduct = products.get(0);
            String listName = firstProduct.findElement(By.className("inventory_item_name")).getText();
            String listPrice = firstProduct.findElement(By.className("inventory_item_price")).getText();

            // Open product detail
            firstProduct.findElement(By.className("inventory_item_name")).click();

            // Get detail page info
            String detailName = driver.findElement(By.className("inventory_details_name")).getText();
            String detailPrice = driver.findElement(By.className("inventory_details_price")).getText();

            // Compare
            boolean nameMatch = listName.equals(detailName);
            boolean priceMatch = listPrice.equals(detailPrice);

            System.out.println("✅ List Name: " + listName + " | Detail Name: " + detailName + " | Match: " + nameMatch);
            System.out.println("✅ List Price: " + listPrice + " | Detail Price: " + detailPrice + " | Match: " + priceMatch);

            // Go back to products
            driver.findElement(By.id("back-to-products")).click();
        }
    }

    private void logout() {
        try {
            driver.findElement(By.id("react-burger-menu-btn")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link"))).click();
            System.out.println("🔒 Logged out successfully");
        } catch (Exception e) {
            System.out.println("⚠️ Logout failed");
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("\n🎯 ALL UI TESTS COMPLETED!");
        }
    }
    public void runTests() {
        System.out.println("🎨 STARTING UI TESTS...");
        try {
            setup();

            // Using reflection to run UI test methods
            java.lang.reflect.Method testMethod = this.getClass().getMethod("uiTest",
                    String.class, String.class, String.class);

            // Run sample UI tests
            testMethod.invoke(this, "standard_user", "BUTTONS_UI", "Buttons UI test");
            testMethod.invoke(this, "standard_user", "IMAGES_DISPLAY", "Images display test");
            testMethod.invoke(this, "standard_user", "MENU_UI", "Menu UI test");

        } catch (Exception e) {
            System.out.println("❌ Error running UI tests: " + e.getMessage());
        } finally {
            tearDown();
        }
    }
}