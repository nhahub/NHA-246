package org.example.tests.base;

import org.example.pages.*;
import org.example.utils.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    protected WebDriver driver;
    protected LoginPage loginPage;
    protected ProductsPage productsPage;
    protected CartPage cartPage;
    protected CheckoutPage checkoutPage;

    @BeforeMethod
    public void setUp() {
        System.out.println("🔄 Setting up test environment...");

        // Create driver using WebDriverManager
        driver = DriverFactory.createDriver();

        // Initialize all pages
        initializePages();

        // Navigate to application
        driver.get("https://www.saucedemo.com");
        System.out.println("🌐 Navigated to: " + driver.getCurrentUrl());
    }

    private void initializePages() {
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            System.out.println("🔚 Closing browser...");
            driver.quit();
        }
    }

    // Helper methods for common operations
    protected void loginAsStandardUser() {
        loginPage.login("standard_user", "secret_sauce");
    }

    protected void loginAsProblemUser() {
        loginPage.login("problem_user", "secret_sauce");
    }

    protected void logout() {
        try {
            driver.findElement(org.openqa.selenium.By.id("react-burger-menu-btn")).click();
            Thread.sleep(500);
            driver.findElement(org.openqa.selenium.By.id("logout_sidebar_link")).click();
        } catch (Exception e) {
            System.out.println("⚠️ Logout failed: " + e.getMessage());
        }
    }
}