# PrimeTester – Automated Testing Project

PrimeTester is a comprehensive automated testing project built with Java, Selenium WebDriver, TestNG, and API Testing with Postman.
The project includes test automation for Login, Cart, Checkout, Sorting, UI validation, and API endpoint verification using https://dummyjson.com.

---

## Features

- Full end-to-end automated test scenarios
- Browser automation using Selenium WebDriver
- API testing via Postman
- Clean separation of test classes by functionality
- Maven project structure with reusable TestRunner
- Main flows covered: Login → Add to Cart → Checkout → Sorting → UI Validation → API Testing

---

## Technologies Used

- Java 8+
- Selenium WebDriver
- TestNG
- Maven
- IntelliJ IDEA
- Postman (for API testing)

---

## Project Structure

PrimeTester/
├── pom.xml
├── testng.xml
├── .idea/
├── .mvn/
├── src/
│   ├── main/java/org/example/
│   │   ├── listeners/         # Test listeners
│   │   ├── pages/             # Page Object Model classes
│   │   ├── utils/             # Helper utilities
│   │   └── App.java           # Optional
│   └── test/java/org/example/tests/
│       ├── base/              # BaseTest setup
│       ├── LoginTest.java
│       ├── CartTest.java
│       ├── CheckoutTest.java
│       ├── SortingTest.java
│       ├── UITest.java
│       ├── YourCartTest.java
│       └── TestRunner.java
└── src/test/resources/
    ├── config.properties      # App configuration
    └── test-data/
        └── users.json         # Test data (optional)

---

## Running Tests

### Run all tests via Maven
mvn test

### Run TestRunner directly from IntelliJ
1. Open TestRunner.java
2. Click Run ▶️

### Run API Tests using Postman
1. Open Postman
2. Import the Postman Collection (if provided)
3. Test endpoints: GET, POST, PUT, DELETE
4. Verify Status Codes and Response Body
5. API endpoints are from https://dummyjson.com

---

## Automated Test Coverage

Test Class      | Coverage
----------------|--------------------------
LoginTest       | Valid login, invalid login, locked user scenarios
CartTest        | Add to cart, remove from cart
YourCartTest    | Cart details, item count verification
CheckoutTest    | User info, checkout steps, order completion
SortingTest     | Sort by name and price
UITest          | UI checks & element visibility
AppTest         | General framework validation
API Tests       | API endpoint verification via Postman

---

## Project Deliverables

- Manual Test Cases (Excel Sheet)
  https://1drv.ms/x/c/95e188fbebbf03c2/IQBe88wM8hsFQL5p8fCT2F45AYu4jxlLR0b-Q16c12gpsos?e=IWVGMD

- Presentation (PrimeTester)
  https://1drv.ms/p/c/28300ce589eb0205/IQBJUWuZHDdPR6xYGTla3PbPARYIY3_5jMUKi7ox5avQH3w?e=spuB9c

- Documentation
  https://drive.google.com/file/d/1s-PwTz472MSqe6MWN3p4KdLbqBMjRrZJ/view?usp=sharing

- Test Plan
  https://drive.google.com/file/d/1lTCrsSZZFHhqiueUrOYPVCF98Z-I2t9a/view?usp=sharing

---

## Authors

- Belal Amr
- Mohamed Ahmed Elhageen
- Omar Ibrahim
- Filopatter Hani
- Muslim Eslam

---

## Project Status

- Manual Test Cases ✔
- Automation Scripts ✔
- API Testing (Postman with https://dummyjson.com) ✔
- Documentation ✔
- Test Plan ✔
- Presentation ✔
- Final delivery completed successfully ✔

---

## POM (Page Object Model) Implementation Highlights

1. Separation of Concerns:
   - pages/ → Page elements & actions
   - tests/ → Test scenarios
   - utils/ → Helper functions

2. Page Objects:
   - BasePage.java → Driver & common methods
   - LoginPage.java → Login actions
   - ProductsPage.java → Product selection & sorting
   - CartPage.java → Cart actions
   - CheckoutPage.java → Checkout process

3. BaseTest.java → Setup & teardown, login helper methods

4. DriverFactory.java → WebDriver management

5. ConfigReader.java → Read application properties

6. Updated tests to use POM instead of direct WebDriver calls

7. Dependencies in pom.xml: Selenium, TestNG, WebDriverManager

8. TestNG suite (testng.xml) for running all tests together
