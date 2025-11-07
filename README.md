# 📦 Datablist Playwright Automation

Automates validation of data integrity when uploading an Excel file to [Datablist](https://app.datablist.com/).  
Built using **Java + Playwright + Apache POI + Maven**.

---

## ✅ Features

- Upload Excel/CSV to Datablist.
- Verify displayed data against source Excel.
- Apply filters:
  - Status = "Active"
  - Amount ≥ 1000
  - CreatedDate = 2023
- Export filtered data and compare with locally filtered dataset.
- Generate validation summary in console.

---

## 🛠️ Prerequisites

- **Java 17+**
- **Maven**
- **Git**
- [Playwright Browsers](https://playwright.dev/java/docs/browsers)

---

## 📁 Project Structure
datablist-playwright-automation
│
├─ src/main/java/com/tests # Test scripts (DatablistUploadTest.java)
├─ src/main/java/com/pages # Page Object classes (HomePage, CollectionPage, ExportPage)
├─ src/main/java/com/utils # Utilities (ExcelReader, ExcelComparator, WebTableExtractor, ConfigReader, etc.)
├─ src/main/java/com/base # BrowserFactory
├─ resources # Source Excel file and config.properties
└─ pom.xml # Maven project file


---

## ⚡ Setup

1. Clone the repository:

```bash
git clone https://github.com/YourUsername/datablist-playwright-automation.git
cd datablist-playwright-automation
mvn clean install
Run the Automation
Using IDE:

Right-click DatablistUploadTest.java → Run As → Java Application.

Using Maven:
mvn compile exec:java -Dexec.mainClass="com.tests.DatablistUploadTest"

Using Terminal (without Maven):
cd src/main/java
javac -cp ".;path\to\poi.jar;path\to\playwright.jar" com\tests\DatablistUploadTest.java
java -cp ".;path\to\poi.jar;path\to\playwright.jar;target\classes" com.tests.DatablistUploadTest

📊 Output

Captured rows from Datablist.

Comparison of Excel vs web data.

Exported filtered data file in /downloads.

Validation summary printed in console.

📝 Notes

Ensure Playwright browsers are installed:

mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install"


Excel file paths in config.properties must match your local setup.

Make sure downloads folder exists or is created by the script.

📂 Author

Neethu Gopi
Email: neethugopiiykkunnel@gmail.com
