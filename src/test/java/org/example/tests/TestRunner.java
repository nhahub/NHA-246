package org.example.tests;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;
import java.util.ArrayList;
import java.util.List;

public class TestRunner {
    public static void main(String[] args) {
        System.out.println("🎯 STARTING ALL TESTS WITH POM PATTERN 🎯");
        System.out.println("=".repeat(50));

        // Create TestNG instance
        TestNG testng = new TestNG();

        // Create suite
        XmlSuite suite = new XmlSuite();
        suite.setName("All Tests Suite");
        suite.setParallel(XmlSuite.ParallelMode.NONE); // ✅ تم التصحيح هنا

        // Create test
        XmlTest test = new XmlTest(suite);
        test.setName("Complete Test Run");

        // Add all test classes
        List<XmlClass> classes = new ArrayList<>();
        classes.add(new XmlClass("org.example.tests.LoginTest"));
        classes.add(new XmlClass("org.example.tests.CartTest"));
        classes.add(new XmlClass("org.example.tests.CheckoutTest"));
        classes.add(new XmlClass("org.example.tests.SortingTest"));
        classes.add(new XmlClass("org.example.tests.UITest"));
        classes.add(new XmlClass("org.example.tests.YourCartTest"));

        test.setXmlClasses(classes);

        // Set suite
        List<XmlSuite> suites = new ArrayList<>();
        suites.add(suite);
        testng.setXmlSuites(suites);

        // Run tests
        testng.run();

        System.out.println("\n" + "=".repeat(50));
        System.out.println("📊 ALL TESTS EXECUTION COMPLETED");
        System.out.println("=".repeat(50));

        // Check exit code
        if (testng.getStatus() == 0) {
            System.out.println("✅ ALL TESTS PASSED SUCCESSFULLY! 🎉");
        } else {
            System.out.println("⚠️  Some tests failed. Check the logs above.");
        }
    }
}