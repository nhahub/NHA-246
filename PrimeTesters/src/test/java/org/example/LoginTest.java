package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
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


public class LoginTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        // Disable Chrome password save prompts and other popups
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-autofill-keyboard-accessory-view");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @DataProvider(name = "LoginData")
    public Object[][] getLoginData() {
        return new Object[][] {
                // TC_001 - TC_005: Valid Logins
                {"standard_user", "secret_sauce", "VALID", "Should redirect to products page"},
                {"problem_user", "secret_sauce", "VALID", "Should redirect to products page"},
                {"performance_glitch_user", "secret_sauce", "VALID", "Should redirect to products page"},
                {"error_user", "secret_sauce", "VALID", "Should redirect to products page"},
                {"visual_user", "secret_sauce", "VALID", "Should redirect to products page"},

                // TC_006 - TC_008: Invalid Logins
                {"invalid_user", "secret_sauce", "INVALID_USER", "Should show error message"},
                {"standard_user", "wrong_password", "INVALID_PASS", "Should show error message"},
                {"locked_out_user", "secret_sauce", "LOCKED", "Should show locked message"},

                // TC_009 - TC_011: Empty Fields
                {"", "secret_sauce", "EMPTY_USER", "Should show username required"},
                {"standard_user", "", "EMPTY_PASS", "Should show password required"},
                {"", "", "EMPTY_BOTH", "Should show username required"}
        };
    }

    @Test(dataProvider = "LoginData")
    public void loginTest(String username, String password, String testType, String description) {
        System.out.println("\n🔍 Testing: " + description);
        System.out.println("👤 Username: " + (username.isEmpty() ? "[EMPTY]" : username));
        System.out.println("🔒 Password: " + (password.isEmpty() ? "[EMPTY]" : "••••••••"));

        driver.get("https://www.saucedemo.com/");

        // Fill login form
        if (!username.isEmpty()) {
            driver.findElement(By.id("user-name")).sendKeys(username);
        }
        if (!password.isEmpty()) {
            driver.findElement(By.id("password")).sendKeys(password);
        }
        driver.findElement(By.id("login-button")).click();

        // Handle Chrome password save prompt
        try {
            Thread.sleep(500);
            // Press ESC to cancel any popups
            driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
        } catch (Exception e) {
            // Ignore if no prompt appears
        }

        // Verify results based on test type
        switch (testType) {
            case "VALID":
                verifyValidLogin();
                break;
            case "INVALID_USER":
            case "INVALID_PASS":
                verifyInvalidCredentials();
                break;
            case "LOCKED":
                verifyLockedUser();
                break;
            case "EMPTY_USER":
            case "EMPTY_PASS":
            case "EMPTY_BOTH":
                verifyEmptyFields(testType);
                break;
        }
    }

    // Verification Methods
    private void verifyValidLogin() {
        try {
            wait.until(ExpectedConditions.urlContains("/inventory.html"));
            System.out.println("✅ PASS - Login successful, redirected to products page");

            // Logout for next test
            logout();
        } catch (Exception e) {
            System.out.println("❌ FAIL - Login failed: " + e.getMessage());
        }
    }

    private void verifyInvalidCredentials() {
        String errorMessage = getErrorMessage();
        if (errorMessage.contains("Username and password do not match")) {
            System.out.println("✅ PASS - Correct error message for invalid credentials");
        } else {
            System.out.println("❌ FAIL - Wrong error message: " + errorMessage);
        }
    }

    private void verifyLockedUser() {
        String errorMessage = getErrorMessage();
        if (errorMessage.contains("Sorry, this user has been locked out")) {
            System.out.println("✅ PASS - Correct locked user message");
        } else {
            System.out.println("❌ FAIL - Wrong error message: " + errorMessage);
        }
    }

    private void verifyEmptyFields(String testType) {
        String errorMessage = getErrorMessage();

        if (testType.equals("EMPTY_USER") || testType.equals("EMPTY_BOTH")) {
            if (errorMessage.contains("Username is required")) {
                System.out.println("✅ PASS - Correct username required message");
            } else {
                System.out.println("❌ FAIL - Wrong error message: " + errorMessage);
            }
        } else if (testType.equals("EMPTY_PASS")) {
            if (errorMessage.contains("Password is required")) {
                System.out.println("✅ PASS - Correct password required message");
            } else {
                System.out.println("❌ FAIL - Wrong error message: " + errorMessage);
            }
        }
    }

    // Helper Methods
    private String getErrorMessage() {
        try {
            WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("[data-test='error']")));
            return errorElement.getText();
        } catch (Exception e) {
            return "No error message found";
        }
    }

    private void logout() {
        try {
            driver.findElement(By.id("react-burger-menu-btn")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link"))).click();
            System.out.println("🔒 Logged out successfully");
        } catch (Exception e) {
            System.out.println("⚠️  Logout failed: " + e.getMessage());
        }
    }

    private void clearFields() {
        try {
            driver.findElement(By.id("user-name")).clear();
            driver.findElement(By.id("password")).clear();
        } catch (Exception e) {
            // Ignore clear errors
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("\n🎯 Browser closed - All tests completed!");
        }
    }
    public void runTests() {
        System.out.println("🔐 STARTING LOGIN TESTS...");
        try {
            // Using reflection to run the actual test methods
            setup();

            // Get the test method using reflection
            java.lang.reflect.Method testMethod = this.getClass().getMethod("loginTest",
                    String.class, String.class, String.class, String.class);

            // Run sample tests
            testMethod.invoke(this, "standard_user", "secret_sauce", "VALID", "Valid login test");
            testMethod.invoke(this, "invalid_user", "secret_sauce", "INVALID_USER", "Invalid user test");
            testMethod.invoke(this, "locked_out_user", "secret_sauce", "LOCKED", "Locked user test");

        } catch (Exception e) {
            System.out.println("❌ Error running login tests: " + e.getMessage());
        } finally {
            tearDown();
        }
    }
}