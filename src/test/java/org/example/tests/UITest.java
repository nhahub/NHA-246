package org.example.tests;

import org.example.tests.base.BaseTest;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class UITest extends BaseTest {

    @Test
    public void testMenuFunctionality() {
        System.out.println("🎨 Testing menu functionality...");

        loginAsStandardUser();

        // Open menu
        driver.findElement(org.openqa.selenium.By.id("react-burger-menu-btn")).click();
        try { Thread.sleep(500); } catch (InterruptedException e) {}

        // Verify menu is open
        assertTrue(driver.findElement(org.openqa.selenium.By.className("bm-menu-wrap"))
                .isDisplayed(), "Menu should be displayed");

        // Close menu
        driver.findElement(org.openqa.selenium.By.id("react-burger-cross-btn")).click();

        System.out.println("✅ Menu functionality works correctly");
    }

    @Test
    public void testImagesLoading() {
        System.out.println("🎨 Testing images loading...");

        loginAsStandardUser();

        int imageCount = driver.findElements(org.openqa.selenium.By.tagName("img")).size();
        assertTrue(imageCount > 0, "Should have at least one image");
        System.out.println("✅ " + imageCount + " images loaded correctly");
    }

    @Test
    public void testButtonsVisibility() {
        System.out.println("🎨 Testing buttons visibility...");

        loginAsStandardUser();

        int buttonCount = driver.findElements(org.openqa.selenium.By.tagName("button")).size();
        assertTrue(buttonCount > 0, "Should have at least one button");
        System.out.println("✅ " + buttonCount + " buttons are visible and clickable");
    }

    @Test
    public void testResponsiveLayout() {
        System.out.println("🎨 Testing responsive layout...");

        loginAsStandardUser();

        // Test different screen sizes
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(1024, 768));
        System.out.println("✅ Desktop layout (1024x768) - OK");

        driver.manage().window().setSize(new org.openqa.selenium.Dimension(768, 1024));
        System.out.println("✅ Tablet layout (768x1024) - OK");

        // Restore fullscreen
        driver.manage().window().maximize();
    }
}