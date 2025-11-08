# 📦 Datablist Playwright Automation

Automates validation of data integrity when uploading an Excel file to [Datablist](https://app.datablist.com/).
Built using Java + Playwright + Apache POI + Maven.
      
---

## ✅ Features

- Upload Excel/CSV file to Datablist.
- Verify displayed data against the source Excel file.
- Apply filters:
  - Status = "Active"
  - Amount ≥ 1000
  - CreatedDate Starts with 2023
- Export filtered data and compare with locally filtered dataset.
- Generate validation summary in the console..

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
├─ src/main/java/com/tests        # Test scripts (DatablistUploadTest.java)
├─ src/main/java/com/pages        # Page Object classes (HomePage, CollectionPage, ExportPage)
├─ src/main/java/com/utils        # Utilities (ExcelReader, ExcelComparator, WebTableExtractor, ConfigReader, etc.)
├─ src/main/java/com/base         # BrowserFactory
├─ resources                      # Source Excel file and config.properties
└─ pom.xml                        # Maven project file


---

## ⚡ Setup & Run

1. Clone the repository:

```bash
**Clone the repository**
git clone https://github.com/NeethuLoshith/datablist-playwright-automation.git
cd datablist-playwright-automation

**Build the project**
mvn clean install

**Install Playwright browsers**
mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install"

**Update configuration**
Edit resources/config.properties
- Set Excel file path to your local source file.
- Set downloads folder path (where exported file will be saved).

▶️ **Run the Automation**
🧩 Option 1 — Using IDE

- Open the project in Eclipse.
- Navigate to com.tests.DatablistUploadTest.java.
- Right-click → Run As → Java Application.

🧩 Option 2 — Using Maven
mvn compile exec:java -Dexec.mainClass="com.tests.DatablistUploadTest"

🧩 Option 3 — Using Terminal (manual compile)
cd src/main/java
javac -cp ".;path\to\poi.jar;path\to\playwright.jar" com\tests\DatablistUploadTest.java
java -cp ".;path\to\poi.jar;path\to\playwright.jar;target\classes" com.tests.DatablistUploadTest


📊 **Output**

- Captured rows from Datablist web table.

- Comparison summary: Excel vs Web Data.

- Exported filtered data saved in /downloads.

- Validation summary printed in the console.

📝 **Notes**

- Ensure Playwright browsers are installed before running.

- Confirm Excel file paths in config.properties are valid.

- Make sure the downloads folder exists or is created by the script.

👩‍💻 **Author**

Neethu Gopi
📧 neethugopiiykkunnel@gmail.com
