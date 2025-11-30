package org.example;

public class TestRunner {
    public static void main(String[] args) {
        System.out.println("=== RUNNING ALL TESTS ===");

        // Run Login Tests
        System.out.println("\n🔐 LOGIN TESTS:");
        LoginTest loginTest = new LoginTest();
        loginTest.runTests();

        // Run Sorting Tests
        System.out.println("\n📊 SORTING TESTS:");
        SortingTest sortingTest = new SortingTest();
        sortingTest.runTests();

        // Run UI Tests
        System.out.println("\n🎨 UI TESTS:");
        UITest uiTest = new UITest();
        uiTest.runTests();

        // Run Cart Tests ⬅️ الجديد
        System.out.println("\n🛒 CART TESTS:");
        CartTest cartTest = new CartTest();
        cartTest.runTests();

        // Run Your Cart Tests
        System.out.println("\n🛒 YOUR CART TESTS:");
        YourCartTest yourCartTest = new YourCartTest();
        yourCartTest.runTests();

        System.out.println("\n✅ ALL TESTS COMPLETED!");
    }
}