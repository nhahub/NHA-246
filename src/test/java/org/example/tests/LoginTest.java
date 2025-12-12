package org.example.tests;

import org.example.tests.base.BaseTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class LoginTest extends BaseTest {

    @DataProvider(name = "loginCredentials")
    public Object[][] getLoginCredentials() {
        return new Object[][] {
                {"standard_user", "secret_sauce", true, "Valid standard user"},
                {"locked_out_user", "secret_sauce", false, "Locked out user"},
                {"problem_user", "secret_sauce", true, "Problem user"},
                {"performance_glitch_user", "secret_sauce", true, "Performance user"},
                {"", "secret_sauce", false, "Empty username"},
                {"standard_user", "", false, "Empty password"},
                {"wrong_user", "wrong_pass", false, "Invalid credentials"}
        };
    }

    @Test(dataProvider = "loginCredentials")
    public void testLoginWithDifferentUsers(String username, String password,
                                            boolean shouldLogin, String description) {
        System.out.println("\n🔍 Testing: " + description);
        System.out.println("👤 Username: " + username);

        loginPage.login(username, password);

        if (shouldLogin) {
            // Verify successful login
            assertTrue(productsPage.isProductsPageDisplayed(),
                    "Should be redirected to products page");
            System.out.println("✅ Login successful");

            // Logout for next test
            logout();
        } else {
            // Verify login failed
            if (username.isEmpty()) {
                assertTrue(loginPage.getErrorMessage().contains("Username is required"));
            } else if (password.isEmpty()) {
                assertTrue(loginPage.getErrorMessage().contains("Password is required"));
            } else if (username.equals("locked_out_user")) {
                assertTrue(loginPage.getErrorMessage().contains("Sorry, this user has been locked out"));
            } else {
                assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"));
            }
            System.out.println("✅ Login failed as expected");
        }
    }

    @Test
    public void testLogoutFunctionality() {
        // Login first
        loginAsStandardUser();
        assertTrue(productsPage.isProductsPageDisplayed());

        // Logout
        logout();

        // Verify logout successful
        assertTrue(loginPage.isLoginPageDisplayed());
        System.out.println("✅ Logout functionality works correctly");
    }
}