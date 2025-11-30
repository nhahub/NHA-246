package org.example;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("===== WELCOME =====");
        System.out.println("1. Run Application");
        System.out.println("2. Run All Tests");
        System.out.print("Choose option (1 or 2): ");

        int choice = scanner.nextInt();

        switch(choice) {
            case 1:
                runApplication();
                break;
            case 2:
                runTests();
                break;
            default:
                System.out.println("Invalid option!");
        }

        scanner.close();
    }

    private static void runApplication() {
        System.out.println("🚀 STARTING MY APPLICATION");

        // الكود الأساسي بتاعك هنا
        System.out.println("1. Login System Initialized");
        System.out.println("2. Sorting Module Loaded");
        System.out.println("3. UI Components Ready");
        System.out.println("4. Cart System Ready"); // ⬅️ إضافة

        System.out.println("🎯 APPLICATION RUNNING SUCCESSFULLY");
    }

    private static void runTests() {
        System.out.println("=== RUNNING ALL TESTS ===");

        // تشغيل Login Tests
        System.out.println("\n🔐 LOGIN TESTS:");
        LoginTest loginTest = new LoginTest();
        loginTest.runTests();

        // تشغيل Sorting Tests
        System.out.println("\n📊 SORTING TESTS:");
        SortingTest sortingTest = new SortingTest();
        sortingTest.runTests();

        // تشغيل UI Tests
        System.out.println("\n🎨 UI TESTS:");
        UITest uiTest = new UITest();
        uiTest.runTests();

        // تشغيل Cart Tests ⬅️ الجديد
        System.out.println("\n🛒 CART TESTS:");
        CartTest cartTest = new CartTest();
        cartTest.runTests();

        System.out.println("\n✅ ALL TESTS COMPLETED!");
    }
}