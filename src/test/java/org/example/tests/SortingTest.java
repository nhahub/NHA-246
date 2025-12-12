package org.example.tests;

import org.example.tests.base.BaseTest;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

import java.util.List;

public class SortingTest extends BaseTest {

    @Test
    public void testSortProductsAZ() {
        System.out.println("📊 Testing A→Z sorting...");

        loginAsStandardUser();
        productsPage.selectSortOption("az");

        List<String> sortedNames = productsPage.getProductNamesList();
        assertTrue(isListSortedAscending(sortedNames),
                "Products should be sorted A→Z");
        System.out.println("✅ A→Z sorting works correctly");
    }

    @Test
    public void testSortProductsZA() {
        System.out.println("📊 Testing Z→A sorting...");

        loginAsStandardUser();
        productsPage.selectSortOption("za");

        List<String> sortedNames = productsPage.getProductNamesList();
        assertTrue(isListSortedDescending(sortedNames),
                "Products should be sorted Z→A");
        System.out.println("✅ Z→A sorting works correctly");
    }

    @Test
    public void testSortPriceLowToHigh() {
        System.out.println("📊 Testing Price Low→High sorting...");

        loginAsStandardUser();
        productsPage.selectSortOption("lohi");

        List<Double> sortedPrices = productsPage.getProductPricesList();
        assertTrue(isListSortedAscending(sortedPrices),
                "Prices should be sorted low to high");
        System.out.println("✅ Price Low→High sorting works correctly");
    }

    @Test
    public void testSortPriceHighToLow() {
        System.out.println("📊 Testing Price High→Low sorting...");

        loginAsStandardUser();
        productsPage.selectSortOption("hilo");

        List<Double> sortedPrices = productsPage.getProductPricesList();
        assertTrue(isListSortedDescending(sortedPrices),
                "Prices should be sorted high to low");
        System.out.println("✅ Price High→Low sorting works correctly");
    }

    // Helper methods for sorting validation
    private <T extends Comparable<T>> boolean isListSortedAscending(List<T> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).compareTo(list.get(i + 1)) > 0) {
                return false;
            }
        }
        return true;
    }

    private <T extends Comparable<T>> boolean isListSortedDescending(List<T> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).compareTo(list.get(i + 1)) < 0) {
                return false;
            }
        }
        return true;
    }
}