# PrimeTester – Two Versions

This repository contains TWO versions of the PrimeTester project:

1. **Main Branch (`main`) – Original Project**
   - Standard automation scripts using Selenium WebDriver and TestNG.
   - Direct WebDriver commands (no Page Object Model).
   - Covers Login, Cart, Checkout, Sorting, UI validation, and API testing.

2. **Updated Branch (`prime-updated`) – POM Design Pattern**
   - Refactored using Page Object Model (POM) for better maintainability.
   - Separation of Pages, Tests, and Utils.
   - Uses DriverFactory and ConfigReader for reusable setup.
   - Same test coverage as the original project but with a scalable design.

---

## Key Notes

- Each version is in its own branch.
- Original project (`main`) is kept as a reference.
- Updated project (`prime-updated`) is the recommended branch for further development.
- Both versions are fully functional and cover all main flows:
  Login → Cart → Checkout → Sorting → UI → API Testing

---

## Authors

- Belal Amr
- Mohamed Ahmed Elhageen
- Omar Ibrahim
- Filopatter Hani
- Muslim Eslam

---

## Status

- Original Project (main) ✔
- Updated POM Project (prime-updated) ✔
- Repository contains both branches for reference ✔
