# PrimeTester – Automation Testing Project

PrimeTester is an automated testing project built using **Java**, **Selenium WebDriver**, and **TestNG**.  
This project includes automation test scripts for Login, Cart, Checkout, Sorting, and general UI validation.

---

## 🚀 Features

- End-to-end automated test scenarios  
- Selenium WebDriver for browser automation  
- Clean test classes separated by functionality  
- Maven project structure  
- Reusable TestRunner for organized execution  
- Covers main flows: Login → Add to Cart → Checkout → Sorting → UI validation

---

## 🛠️ Technologies Used

- Java 8+  
- Selenium WebDriver  
- TestNG  
- Maven  
- IntelliJ IDEA  

---

## 📁 Project Structure

```
Final-project/
├── src
│   ├── main
│   │   └── java
│   │       └── org.example
│   └── test
│       └── java
│           └── org.example
│               ├── App.java
│               ├── AppTest.java
│               ├── CartTest.java
│               ├── CheckoutTest.java
│               ├── LoginTest.java
│               ├── SortingTest.java
│               ├── TestRunner.java
│               ├── UITest.java
│               └── YourCartTest.java
├── pom.xml
├── .gitignore
└── target/
```

---

## ▶️ Running Tests

### **Run the full project using Maven**
```bash
mvn test
```

### **Run TestRunner directly from IntelliJ**
- افتح **TestRunner.java**  
- دوس Run ▶️

---

## 🧪 Automated Test Coverage

| Test Class       | What It Covers |
|------------------|----------------|
| LoginTest        | Valid login, invalid login, locked user |
| CartTest         | Add to cart, remove from cart |
| YourCartTest     | Cart details, item count |
| CheckoutTest     | User info, checkout steps, finish order |
| SortingTest      | Sort by name & price |
| UITest           | UI checks & element visibility |
| AppTest          | General framework testing |

---

## 📎 Project Deliverables

### **🟩 Manual Test Cases (Excel)**
🔗 https://1drv.ms/x/c/95e188fbebbf03c2/IQBe88wM8hsFQL5p8fCT2F45AYu4jxlLR0b-Q16c12gpsos?e=IWVGMD

### **🟦 Presentation (PrimeTester)**
🔗 https://1drv.ms/p/c/28300ce589eb0205/IQBJUWuZHDdPR6xYGTla3PbPARYIY3_5jMUKi7ox5avQH3w?e=spuB9c

### **📘 Documentation**
🔗 https://drive.google.com/file/d/1s-PwTz472MSqe6MWN3p4KdLbqBMjRrZJ/view?usp=sharing

### **📕 Test Plan**
🔗 https://drive.google.com/file/d/1lTCrsSZZFHhqiueUrOYPVCF98Z-I2t9a/view?usp=sharing

---

## 👤 Authors

- Belal Amr  
- Mohamed Ahmed Elhageen  
- Omar Ibrahim  
- Filopatter Hani  
- Muslim Easlam  

---

## ✅ Project Status

✔ Manual Test Cases  
✔ Automation Scripts  
✔ Documentation  
✔ Test Plan  
✔ Presentation  
✔ Final delivery completed successfully
