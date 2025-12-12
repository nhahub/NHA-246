package org.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutPage extends BasePage {

    @FindBy(id = "first-name")
    private WebElement firstNameInput;

    @FindBy(id = "last-name")
    private WebElement lastNameInput;

    @FindBy(id = "postal-code")
    private WebElement postalCodeInput;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(id = "finish")
    private WebElement finishButton;

    @FindBy(className = "complete-header")
    private WebElement completeHeader;

    @FindBy(className = "summary_total_label")
    private WebElement totalPrice;

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void enterShippingInfo(String firstName, String lastName, String postalCode) {
        firstNameInput.clear();
        firstNameInput.sendKeys(firstName);
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);
        postalCodeInput.clear();
        postalCodeInput.sendKeys(postalCode);
    }

    public void continueToOverview() {
        continueButton.click();
    }

    public void completePurchase() {
        finishButton.click();
    }

    public String getConfirmationMessage() {
        return completeHeader.getText();
    }

    public String getTotalPrice() {
        return totalPrice.getText();
    }

    public boolean isCheckoutPageDisplayed() {
        return firstNameInput.isDisplayed();
    }

    public boolean isCheckoutComplete() {
        return completeHeader.getText().contains("Thank you for your order");
    }
}