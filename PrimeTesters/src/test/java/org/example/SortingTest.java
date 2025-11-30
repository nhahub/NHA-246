package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SortingTest {

    static String[] users = {
            "standard_user",
            "problem_user",
            "performance_glitch_user",
            "error_user",
            "visual_user",
            "locked_out_user"
    };

    public static void main(String[] args) throws InterruptedException {
        // ✅ FIXED: Remove WebDriverManager and use direct ChromeDriver
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        for (String username : users) {
            System.out.println("   RUNNING TESTS FOR USER: " + username);

            driver.get("https://www.saucedemo.com/");

            Map<String, String> results = new LinkedHashMap<>();

            // LOGIN
            driver.findElement(By.id("user-name")).sendKeys(username);
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();
            Thread.sleep(1000);

            // ✅ FIXED: Use correct error message selector
            boolean loginFailed = driver.findElements(By.cssSelector("[data-test='error']")).size() > 0;

            if (loginFailed) {
                results.put("Login", "❌ Failed (Invalid login)");
                printUserSummary(username, results);
                continue; // next user
            } else {
                results.put("Login", "✔ Passed (Login successful)");
            }

            // SORTING TESTS
            results.put("Sort A→Z", testSorting(driver, "az"));
            results.put("Sort Z→A", testSorting(driver, "za"));
            results.put("Sort Low→High", testSorting(driver, "lohi"));
            results.put("Sort High→Low", testSorting(driver, "hilo"));

            // LOGOUT
            try {
                driver.findElement(By.id("react-burger-menu-btn")).click();
                Thread.sleep(500);
                driver.findElement(By.id("logout_sidebar_link")).click();
                results.put("Logout", "✔ Success");
            } catch (Exception e) {
                results.put("Logout", "⚠ Failed");
            }

            Thread.sleep(500);
            printUserSummary(username, results);
        }

        driver.quit();
        System.out.println("🎯 ALL TESTS COMPLETED!");
    }

    // SORTING TEST
    public static String testSorting(WebDriver driver, String value) {
        try {
            WebElement dropdown = driver.findElement(By.className("product_sort_container"));
            Select sort = new Select(dropdown);
            sort.selectByValue(value);

            Thread.sleep(1000);

            List<WebElement> products = driver.findElements(By.className("inventory_item"));

            List<String> names = new ArrayList<>();
            List<Double> prices = new ArrayList<>();

            for (WebElement product : products) {
                names.add(product.findElement(By.className("inventory_item_name")).getText());
                prices.add(Double.parseDouble(
                        product.findElement(By.className("inventory_item_price"))
                                .getText().replace("$", "")
                ));
            }

            boolean passed = false;

            switch (value) {
                case "az" -> passed = isSortedAscending(names);
                case "za" -> passed = isSortedDescending(names);
                case "lohi" -> passed = isSortedAscending(prices);
                case "hilo" -> passed = isSortedDescending(prices);
            }

            return passed ? "✔ Passed" : "✘ Failed";

        } catch (Exception e) {
            return "✘ Failed";
        }
    }

    // PRINT SUMMARY FOR EACH USER
    public static void printUserSummary(String username, Map<String, String> results) {
        System.out.println("\n---------- SUMMARY FOR USER: " + username + " ----------");
        results.forEach((testName, status) -> {
            System.out.println(testName + ": " + status);
        });
        System.out.println("----------------------------------------------------\n");
    }

    // SORT CHECKERS
    public static <T extends Comparable<T>> boolean isSortedAscending(List<T> list) {
        return list.equals(list.stream().sorted().toList());
    }

    public static <T extends Comparable<T>> boolean isSortedDescending(List<T> list) {
        return list.equals(list.stream().sorted(Comparator.reverseOrder()).toList());
    }
    public void runTests() {
        System.out.println("📊 STARTING SORTING TESTS...");
        try {
            // Call the main method which contains all the sorting tests
            String[] args = {};
            java.lang.reflect.Method mainMethod = SortingTest.class.getMethod("main", String[].class);
            mainMethod.invoke(null, (Object) args);
        } catch (Exception e) {
            System.out.println("❌ Error running sorting tests: " + e.getMessage());
        }
    }
}
