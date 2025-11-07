# Datablist Playwright Automation

This project automates uploading an Excel file to [Datablist](https://app.datablist.com/) and validates data integrity using Playwright and Java.
## Features
- Upload Excel to Datablist
- Capture web table rows
- Compare with Excel data
- Apply filters and export
- Validate exported data

## Tech Stack
- Java
- Maven
- Playwright for Java
- Apache POI for Excel handling

## 📦 Structure
- **scripts/** – Helper shell scripts for setup & testing
- **src/main/java/** – Utilities like ExcelComparator
- **src/test/java/** – Playwright test cases
- **resources/** – Input Excel files
- **.github/workflows/** – CI pipeline

## 🚀 Run Locally
```bash
mvn clean test
```

## 🧪 CI/CD
Automatically runs on each push (see `.github/workflows/ci.yml`)
