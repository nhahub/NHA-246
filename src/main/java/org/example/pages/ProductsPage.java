package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import java.util.List;

public class ProductsPage extends BasePage {

    @FindBy(className = "inventory_item")
    private List<WebElement> productItems;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartIcon;

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(className = "product_sort_container")
    private WebElement sortDropdown;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> productNames;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> productPrices;

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public void addProductToCart(String productName) {
        String buttonId = "add-to-cart-" + productName.toLowerCase().replace(" ", "-");
        driver.findElement(By.id(buttonId)).click();
    }

    public void removeProductFromCart(String productName) {
        String buttonId = "remove-" + productName.toLowerCase().replace(" ", "-");
        driver.findElement(By.id(buttonId)).click();
    }

    public void goToCart() {
        cartIcon.click();
    }

    public String getCartBadgeCount() {
        return cartBadge.isDisplayed() ? cartBadge.getText() : "0";
    }

    public boolean isCartBadgeDisplayed() {
        return cartBadge.isDisplayed();
    }

    public int getProductCount() {
        return productItems.size();
    }

    public void selectSortOption(String option) {
        Select dropdown = new Select(sortDropdown);
        dropdown.selectByValue(option);
    }

    public List<String> getProductNamesList() {
        return productNames.stream()
                .map(WebElement::getText)
                .toList();
    }

    public List<Double> getProductPricesList() {
        return productPrices.stream()
                .map(element -> Double.parseDouble(element.getText().replace("$", "")))
                .toList();
    }

    public boolean isProductsPageDisplayed() {
        return !productItems.isEmpty();
    }
}