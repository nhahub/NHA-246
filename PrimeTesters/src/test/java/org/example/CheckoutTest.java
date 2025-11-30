package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CheckoutTest {
    public static void main(String[] args) {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-password-manager-reauth");
        options.addArguments("--disable-notifications");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.get("https://www.saucedemo.com/");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name"))).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-sauce-labs-backpack"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.className("shopping_cart_link"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("checkout"))).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name"))).sendKeys("Hany");
            driver.findElement(By.id("last-name")).sendKeys("Mekhail");
            driver.findElement(By.id("postal-code")).sendKeys("12345");
            driver.findElement(By.id("continue")).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("finish"))).click();
            WebElement msg = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("complete-header")));
            System.out.println("CHECKOUT SUCCESS: " + msg.getText());
        } finally {
            driver.quit();
        }
    }
}
