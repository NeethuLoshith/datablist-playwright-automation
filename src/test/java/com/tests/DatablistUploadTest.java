package com.tests;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import com.base.BrowserFactory;
import com.microsoft.playwright.Download;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.pages.CollectionPage;
import com.pages.ExportPage;
import com.pages.HomePage;
import com.utils.ConfigReader;
import com.utils.ExcelComparator;
import com.utils.ExcelFilter;
import com.utils.ExcelReader;
import com.utils.PrintUtils;
import com.utils.WebTableExtractor;

public class DatablistUploadTest {

	public static void main(String[] args) throws Exception {
		// ✅ Read from config file

		String excelPath = ConfigReader.get("excel.path");
		Path downloadsDir = Paths.get(ConfigReader.get("download.dir"));
		// Setup
		List<Map<String, String>> excelData = ExcelReader.readExcelFile(excelPath);
		System.out.println("✅ Excel Loaded: " + excelData.size() + " rows");

		Page page = BrowserFactory.initBrowser(false);
		HomePage home = new HomePage(page);
		CollectionPage collection = new CollectionPage(page);
		ExportPage export = new ExportPage(page, downloadsDir);

		// Step 1: Upload
		home.open();
		home.uploadFile(Paths.get(excelPath));
		home.completeImport();

		// Step 2: Extract first 22 visible rows
		List<Map<String, String>> visibleWeb = WebTableExtractor.extractVisibleRows(page, 22);
		System.out.println("✅ Captured web rows: " + visibleWeb.size());

		PrintUtils.printRows(excelData, 0, 22, "Excel Data (first 22 rows)");
		PrintUtils.printRows(visibleWeb, 0, visibleWeb.size(), "Visible Web Data");

		ExcelComparator.compare(excelData.subList(0, 22), visibleWeb);

		// Step 4: Extract remaining rows
		List<Map<String, String>> allWebRows = WebTableExtractor.extractAllRows(page);
		List<Map<String, String>> remainingWebRows = allWebRows.subList(Math.min(22, allWebRows.size()),
				allWebRows.size());

		PrintUtils.printRows(excelData, 22, excelData.size(), "Excel Data (rows 23–40)");
		PrintUtils.printRows(remainingWebRows, 0, remainingWebRows.size(), "Web Data (rows 23–40)");

		ExcelComparator.compare(excelData.subList(22, 40), remainingWebRows);

		// Step 5: Apply filters & export
		collection.applyFilters();

		// ✅ Step 6: Export filtered data
		if (!Files.exists(downloadsDir))
			Files.createDirectories(downloadsDir);
		Download download = page.waitForDownload(() -> {
			page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Export")).click();
			page.getByText("Export filtered items").click();
			page.locator("#modal").getByRole(AriaRole.BUTTON, new Locator.GetByRoleOptions().setName("Export")).click();
		});

		Path exportedFile = downloadsDir.resolve("exported_filtered.xlsx");
		download.saveAs(exportedFile);
		System.out.println("✅ Export saved to: " + exportedFile);

		Path exportedFile1 = export.exportFilteredData();
		System.out.println("✅ Exported to: " + exportedFile1);
		// ✅ Step 7: Read exported data (XLSX or CSV)
		List<Map<String, String>> exportedData;
		try {
			exportedData = ExcelReader.readExcelFile(exportedFile1.toString());
		} catch (Exception e) {
			System.out.println("⚠️ Export not XLSX, reading as CSV.");
			exportedData = ExcelReader.readCsvFile(exportedFile1.toString());
		}

		// ✅ Step 8: Apply local filters and compare
		List<Map<String, String>> localFiltered = ExcelFilter.applyFilters(excelData);
		PrintUtils.printRows(localFiltered, 0, Math.min(10, localFiltered.size()), "Locally Filtered Excel Data");
		ExcelComparator.compare(localFiltered, exportedData);

		// ✅ Step 9: Validation summary
		System.out.println("\n✅ Final Validation Summary:");
		System.out.println("→ Excel total rows: " + excelData.size());
		System.out.println("→ Web captured rows: " + allWebRows.size());
		System.out.println("→ Locally filtered rows: " + localFiltered.size());
		System.out.println("→ Exported filtered rows: " + exportedData.size());

		if (localFiltered.size() == exportedData.size()) {
			System.out.println("✅ Export matches filtered data count perfectly!");
		} else {
			System.out.println("❌ Mismatch in filtered vs exported row count!");
		}
		BrowserFactory.closeBrowser();
	}
}
